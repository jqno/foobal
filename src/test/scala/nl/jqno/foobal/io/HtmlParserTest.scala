package nl.jqno.foobal.io

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.mockito.Mockito.when
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

import nl.jqno.foobal.test_data.SampleData._

@RunWith(classOf[JUnitRunner])
class HtmlParserTest extends FlatSpec with ShouldMatchers with MockitoSugar {
  val clock = mock[DateFactory]
  val parser = new HtmlParser(clock)
  
  
  behavior of "A Parser"
  
  it should "parse valid data" in {
    seasonEndYear(2011)
    parser.parse(ValidHtml_1) should be (ValidOutcomes_1)
  }
  
  it should "ignore the header columns" in {
    seasonEndYear(2011)
    parser.parse(ValidHtmlWithHeaders_1) should be (ValidOutcomes_1)
  }
  
  it should "be able to cope with slightly malformed html" in {
    seasonEndYear(2011)
    parser.parse(ValidButMalformedHtml_1) should be (ValidOutcomes_1)
  }
  
  it should "parse valid data in multiple tables" in {
    seasonEndYear(2013)
    parser.parse(ValidHtml_2) should be (ValidOutcomes_2)
  }
  
  it should "ignore table elements not marked with class 'schema'" in {
    seasonEndYear(2012)
    parser.parse(HtmlWithNonSchemaTables) should be (OutcomesWithNonSchemaTables)
  }
  
  it should "not fail on the dtd" in {
    parser.parse(HtmlWithDtd)
  }
  
  def seasonEndYear(year: Int) {
    when (clock.today) thenReturn (new LocalDate(year, 1, 1))
  }
}
