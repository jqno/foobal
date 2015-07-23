package nl.jqno.foobal.io

import nl.jqno.foobal.domain.Outcome
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import org.joda.time.LocalDate

import scala.util.Try
import scala.xml.{Node, XML}

class HtmlParser(clock: DateFactory = new DateFactory) {
  def parse(input: String): List[Outcome] = {
    val xml = toXml(input)

    val events = (xml \\ "div") filter hasAttributeWith("events")
    val past = (events \ "div") filter hasAttributeWith("past")
    var date: Option[LocalDate] = None
    val result = (past \ "_").map { e =>
      e.label match {
        case "b" =>
          date = Try { parseDate(e.text.trim) }.toOption
          None
        case "div" =>
          val rawScore = (e \\ "div") filter hasAttributeWith("center score")
          val scores = rawScore.text.split("-").map(_.trim)
          val rawHomeTeam = (e \\ "div") filter hasAttributeWith("float-left club")
          val homeTeam = rawHomeTeam.text.trim
          val rawOutTeam = (e \\ "div") filter hasAttributeWith("float-right club")
          val outTeam = rawOutTeam.text.trim

          for {
            d <- date
          } yield Outcome(homeTeam, outTeam, scores(0).toInt, scores(1).toInt, d)
        case _ => None
      }
    }

    result.toList.flatten
  }

  private def toXml(input: String): scala.xml.Elem = {
    val parser = new SAXFactoryImpl().newSAXParser
    XML.withSAXParser(parser).loadString(input)
  }

  private def hasAttributeWith(value: String)(node: Node): Boolean =
    node.attributes exists (_.value.text == value)
  
  private def toOutcome(homeTeam: String, outTeam: String, scores: Array[String], date: LocalDate): Option[Outcome] = {
    Some(Outcome(homeTeam, outTeam, scores(0).toInt, scores(1).toInt, date))
  }

  private def parseDate(input: String): LocalDate = {
    val split = input.split(" ")
    val day = split(0).toInt
    val month = months(split(1))
    val year = split(2).toInt
    new LocalDate(year, month, day)
  }

  private val months = Map(
    "januari" -> 1, "februari" -> 2, "maart" -> 3,
    "april" -> 4, "mei" -> 5, "juni" -> 6,
    "juli" -> 7, "augustus" -> 8, "september" -> 9,
    "oktober" -> 10, "november" -> 11, "december" -> 12
  )
}