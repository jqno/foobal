package nl.jqno.foobal.updateoutcomes

import org.junit.runner.RunWith
import org.mockito.Matchers.any
import org.mockito.Matchers.anyString
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.when
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatest.FlatSpec
import org.scalatest.OneInstancePerTest
import com.nummulus.boite.Failure
import com.nummulus.boite.Full
import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.io.SampleData.VALID_1_HTML
import nl.jqno.foobal.io.SampleData.VALID_1_OUTCOMES
import nl.jqno.foobal.io.SampleData.VALID_2_HTML
import nl.jqno.foobal.io.SampleData.VALID_2_OUTCOMES
import nl.jqno.foobal.io.Downloader
import nl.jqno.foobal.io.Files
import nl.jqno.foobal.io.HtmlParser
import com.nummulus.boite.Box

@RunWith(classOf[JUnitRunner])
class OutcomesUpdaterTest extends FlatSpec with ShouldMatchers with OneInstancePerTest with MockitoSugar {
  val files = mock[Files]
  val parser = mock[HtmlParser]
  val downloader = mock[Downloader]
  val updater = new OutcomesUpdater(downloader, parser, files)
  
  behavior of "An OutcomesUpdater"
  
  it should "download outcomes and update the XML file" in {
    val fileName = "/some/file_name"
    upload(Full(VALID_1_HTML), VALID_1_OUTCOMES)
    
    updater.update(fileName)
    
    verify(files).exportTo(fileName, VALID_1_OUTCOMES)
  }
  
  it should "download other outcomes and update the XML file" in {
    val fileName = "/another/file_name"
    upload(Full(VALID_2_HTML), VALID_2_OUTCOMES)
    
    updater.update(fileName)
    
    verify(files).exportTo(fileName, VALID_2_OUTCOMES)
  }
  
  it should "not update the XML if the downloader fails" in {
    upload(Failure("uh-oh"))
    
    updater.update("")
    
    verify(files, never).exportTo(anyString, any(classOf[List[Outcome]]))
  }
  
  def upload(content: Box[String], result: List[Outcome] = Nil) = {
    when(downloader.fetch) thenReturn content
    content.foreach { s => when(parser.parse(s)) thenReturn result }
  }
}