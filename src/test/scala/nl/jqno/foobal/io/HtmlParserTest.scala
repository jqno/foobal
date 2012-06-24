package nl.jqno.foobal.io

import nl.jqno.foobal.domain.Outcome

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.joda.time.LocalDate

import SampleData._

@RunWith(classOf[JUnitRunner])
class HtmlParserTest extends FlatSpec with ShouldMatchers {
  val parser = new HtmlParser
  
  behavior of "A Parser"
  
  it should "parse valid data" in {
    parser.parse(VALID_1_HTML, 2011) should be (VALID_1_OUTCOMES)
  }
  
  it should "parse valid data in multiple tables" in {
    parser.parse(VALID_2_HTML, 2012) should be (VALID_2_OUTCOMES)
  }
  
  it should "ignore the dtd" in {
    parser.parse(DTD, 0)
  }
}
