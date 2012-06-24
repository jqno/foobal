package nl.jqno.foobal.io

import scala.xml.XML

import org.joda.time.LocalDate

import nl.jqno.foobal.domain.Outcome

class HtmlParser {
  def parse(input: String, seasonEndYear: Int): Set[Outcome] = {
    val xml = toXml(input)
    
    val x = (xml \\ "body" \\ "table" \\ "tr").map { elem =>
      val data = (elem \\ "td").map(_.text.trim)
      val scores = data(3).split("-").map(_.toInt)
      Outcome(data(1), data(2), scores(0), scores(1), parseDate(data(0), seasonEndYear))
    }
    
    x.toSet
  }
  
  private def toXml(input: String): scala.xml.Elem = {
    val withoutDtd = input.dropWhile(_ != '\n')
    XML.loadString(withoutDtd)
  }
  
  private def parseDate(input: String, seasonEndYear: Int): LocalDate = {
    val split = input.split(" |, ")
    val day = split(1).toInt
    val month = MONTHS(split(2))
    val year = if (month > 6) seasonEndYear - 1 else seasonEndYear
    new LocalDate(year, month, day)
  }
  
  private val MONTHS = Map(
      "jan" -> 1, "feb" ->  2, "mrt" ->  3, "apr" ->  4,
      "mei" -> 5, "jun" ->  6, "jul" ->  7, "aug" ->  8,
      "sep" -> 9, "okt" -> 10, "nov" -> 11, "dec" -> 12
  )
}