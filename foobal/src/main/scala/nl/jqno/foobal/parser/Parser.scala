package nl.jqno.foobal.parser

import org.joda.time.LocalDate
import scala.xml.XML
import scala.xml.Elem
import scala.xml.Node

class Parser {
  def parse(input: String): Set[Outcome] = {
    val xml = XML.loadString(input)
    
    val x = (xml \\ "body" \\ "table" \\ "tr").map { elem =>
      val data = (elem \\ "td").map(_.text.trim)
      val scores = data(3).split("-").map(_.toInt)
      Outcome(data(1), data(2), scores(0), scores(1), parseDate(data(0)))
    }
    
    x.toSet
  }
  
  private def parseDate(input: String): LocalDate = {
    val split = input.split(" |, ")
    val day = split(1).toInt
    val month = MONTHS(split(2))
    new LocalDate(2012, month, day)
  }
  
  private val MONTHS = Map(
      "jan" -> 1, "feb" ->  2, "mrt" ->  3, "apr" ->  4,
      "mei" -> 5, "jun" ->  6, "jul" ->  7, "aug" ->  8,
      "sep" -> 9, "okt" -> 10, "nov" -> 11, "dec" -> 12
  )
}