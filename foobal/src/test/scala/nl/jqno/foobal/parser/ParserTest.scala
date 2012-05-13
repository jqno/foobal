package nl.jqno.foobal.parser

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.joda.time.LocalDate
import ParserTestSampleData._

@RunWith(classOf[JUnitRunner])
class ParserTest extends FlatSpec with ShouldMatchers {
  val parser = new Parser
  
  behavior of "A Parser"
  
  it should "parse valid data" in {
    val expected = Set(
        Outcome("PSV", "Feyenoord", 10, 0, new LocalDate(2012, 10, 24)),
        Outcome("Ajax", "NAC", 1, 2, new LocalDate(2012, 11, 11)))
    parser.parse(VALID_SAMPLE_1) should be (expected)
  }
  
  it should "parse valid data in multiple tables" in {
    val expected = Set(
        Outcome("NAC", "PSV", 2, 0, new LocalDate(2012, 5, 17)),
        Outcome("FC Appelmoesboerdonk", "Willem II", 2, 1, new LocalDate(2012, 5, 18)),
        Outcome("PSV", "NAC", 1, 1, new LocalDate(2012, 5, 24)),
        Outcome("Willem II", "FC Appelmoesboerdonk", 2, 1, new LocalDate(2012, 5, 25)))
    parser.parse(VALID_SAMPLE_2) should be (expected)
  }
  
  it should "ignore the dtd" in (pending)
}
