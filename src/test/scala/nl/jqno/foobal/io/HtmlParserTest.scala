package nl.jqno.foobal.io

import nl.jqno.foobal.domain.Outcome
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.joda.time.LocalDate
import SampleData._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class HtmlParserTest extends FlatSpec with ShouldMatchers with MockitoSugar {
  val clock = mock[DateFactory]
  val parser = new HtmlParser(clock)
  
  behavior of "A Parser"
  
  it should "parse valid data" in {
    seasonEndYear(2011)
    parser.parse(VALID_1_HTML) should be (VALID_1_OUTCOMES)
  }
  
  it should "parse valid data in multiple tables" in {
    seasonEndYear(2012)
    parser.parse(VALID_2_HTML) should be (VALID_2_OUTCOMES)
  }
  
  it should "ignore the dtd" in {
    parser.parse(DTD)
  }
  
  def seasonEndYear(year: Int) = when(clock.today) thenReturn (new LocalDate(year, 1, 1))
}
