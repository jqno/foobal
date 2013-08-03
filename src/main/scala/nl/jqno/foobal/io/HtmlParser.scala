package nl.jqno.foobal.io

import scala.xml.XML
import org.joda.time.LocalDate
import nl.jqno.foobal.domain.Outcome
import com.nummulus.boite.Box
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import nl.jqno.foobal.domain.DateUtil

class HtmlParser(clock: DateFactory = new DateFactory) {
  def parse(input: String): List[Outcome] = {
    val xml = toXml(input)
    
    val x = (xml \\ "body" \\ "table")
      .filter  { table => (table \\ "@class").text startsWith "schema" }
      .flatMap { table =>
        (table \\ "tr") map { elem =>
          Box wrap {
            val data = (elem \\ "td") map { _.text.trim }
            val scores = data(3).split("-") map { _.toInt }
            Outcome(data(1), data(2), scores(0), scores(1), parseDate(data(0)))
          }
        }
      }
    
    x.toList.flatten
  }
  
  private def toXml(input: String): scala.xml.Elem = {
    val parser = new SAXFactoryImpl().newSAXParser
    XML.withSAXParser(parser).loadString(input)
  }
  
  private def parseDate(input: String): LocalDate = {
    val seasonEndYear = DateUtil.determineSeasonEndYearFor(clock.today)
    val split = input split " +|, "
    val day = split(1).toInt
    val month = Months(split(2))
    val year = if (month > DateUtil.SeasonEndMonth) seasonEndYear - 1 else seasonEndYear
    new LocalDate(year, month, day)
  }

  private val Months = Map(
      "jan" -> 1, "feb" ->  2, "mrt" ->  3, "apr" ->  4,
      "mei" -> 5, "jun" ->  6, "jul" ->  7, "aug" ->  8,
      "sep" -> 9, "okt" -> 10, "nov" -> 11, "dec" -> 12
  )
}