package nl.jqno.foobal.io

import nl.jqno.foobal.domain.Outcome

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.joda.time.LocalDate

import HtmlParserTestSampleData._

@RunWith(classOf[JUnitRunner])
class HtmlParserTest extends FlatSpec with ShouldMatchers {
  val parser = new HtmlParser
  
  behavior of "A Parser"
  
  it should "parse valid data" in {
    val expected = Set(
        Outcome("PSV", "Feyenoord", 10, 0, new LocalDate(2010, 10, 24)),
        Outcome("Ajax", "NAC", 1, 2, new LocalDate(2010, 11, 11)))
    parser.parse(VALID_1, 2011) should be (expected)
  }
  
  it should "parse valid data in multiple tables" in {
    val expected = Set(
        Outcome("NAC", "PSV", 2, 0, new LocalDate(2012, 5, 17)),
        Outcome("FC Appelmoesboerdonk", "Willem II", 2, 1, new LocalDate(2012, 5, 18)),
        Outcome("PSV", "NAC", 1, 1, new LocalDate(2012, 5, 24)),
        Outcome("Willem II", "FC Appelmoesboerdonk", 2, 1, new LocalDate(2012, 5, 25)))
    parser.parse(VALID_2, 2012) should be (expected)
  }
  
  it should "ignore the dtd" in {
    parser.parse(DTD, 0)
  }
}
