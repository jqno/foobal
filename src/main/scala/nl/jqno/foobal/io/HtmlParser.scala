package nl.jqno.foobal.io

import scala.xml.XML

import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import org.joda.time.LocalDate

import nl.jqno.foobal.domain.Outcome

class HtmlParser(clock: DateFactory = new DateFactory) {
  def parse(input: String): List[Outcome] = {
    val xml = toXml(input)
    
    val rows = xml \\ "table" \\ "tr"
    val result = rows map { e =>
      val data = (e \\ "td") map (_.text.trim)
      if (data.size <= 5)
        None
      else
        Some(Outcome(data(2), data(3), data(4).toInt, data(5).toInt, parseDate(data(1))))
    }
    result.toList.flatten
  }

  private def toXml(input: String): scala.xml.Elem = {
    val parser = new SAXFactoryImpl().newSAXParser
    XML.withSAXParser(parser).loadString(input)
  }

  private def parseDate(input: String): LocalDate = {
    val split = input.split("-")
    val day = split(2).toInt
    val month = split(1).toInt
    val year = split(0).toInt
    new LocalDate(year, month, day)
  }
}