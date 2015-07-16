package nl.jqno.foobal.io

import nl.jqno.foobal.test_data.SampleData._
import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.mockito.Mockito.when
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class HtmlParserTest extends FlatSpec with Matchers with MockitoSugar {
  val clock = mock[DateFactory]
  val parser = new HtmlParser(clock)
  
  
  behavior of "A Parser"
  
  it should "parse valid data" in {
    seasonEndYear(2011)
    parser.parse(validHtml1) should be (validOutcomes1)
  }
  
  it should "be able to cope with slightly malformed html" in {
    seasonEndYear(2011)
    parser.parse(validButMalformedHtml1) should be (validOutcomes1)
  }
  
  it should "be able to cope with invalid individual entries" in {
    seasonEndYear(2011)
    parser.parse(validHtmlWithInvalidIgnoreableEntry1) should be (validOutcomes1)
  }
  
  it should "parse valid data in multiple tables" in {
    seasonEndYear(2013)
    parser.parse(validHtml2) should be (validOutcomes2)
  }
  
  it should "ignore table elements in <th> tags" in {
    seasonEndYear(2012)
    parser.parse(htmlWithNonContentTables) should be (outcomesWithNonContentTables)
  }
  
  it should "not fail on the dtd" in {
    parser.parse(htmlWithDtd)
  }

  def seasonEndYear(year: Int): Unit =
    when (clock.today) thenReturn (new LocalDate(year, 1, 1))
}
