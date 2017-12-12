package nl.jqno.foobal.io

import nl.jqno.foobal.test_data.SampleData._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class HtmlParserTest extends FlatSpec with Matchers with MockitoSugar {
  val clock = mock[DateFactory]
  val parser = new HtmlParser(clock)
  
  
  behavior of "A Parser"

  it should "parse valid data" in {
    parser.parse(validHtml1) should be (validOutcomes1)
  }

  it should "not crash on a cancellation" in {
    parser.parse(validHtmlWithCancellation) should be (validOutcomes1.drop(1))
  }

  it should "be able to cope with slightly malformed html" in {
    parser.parse(validButMalformedHtml1) should be (validOutcomes1)
  }

  it should "be able to cope with invalid individual entries" in {
    parser.parse(validHtmlWithInvalidIgnoreableEntry1) should be (validOutcomes1)
  }

  it should "parse valid data in multiple tables" in {
    parser.parse(validHtml2) should be (validOutcomes2)
  }

  it should "not fail on the dtd" in {
    parser.parse(htmlWithDtd)
  }
}
