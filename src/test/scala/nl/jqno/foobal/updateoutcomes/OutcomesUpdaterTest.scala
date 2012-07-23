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
import nl.jqno.foobal.io.Url
import java.io.FileNotFoundException
import org.joda.time.LocalDate

@RunWith(classOf[JUnitRunner])
class OutcomesUpdaterTest extends FlatSpec with ShouldMatchers with OneInstancePerTest with MockitoSugar {
  val files = mock[Files]
  val parser = mock[HtmlParser]
  val downloader = mock[Downloader]
  val updater = new OutcomesUpdater(downloader, parser, files)
  
  behavior of "An OutcomesUpdater"
  
  it should "download outcomes and update the XML file" in {
    createEmptyFile
    upload(Full(VALID_1_HTML), VALID_1_OUTCOMES)
    
    update
    
    verifyWritten(VALID_1_OUTCOMES)
  }
  
  it should "download other outcomes and update the XML file" in {
    createEmptyFile
    upload(Full(VALID_2_HTML), VALID_2_OUTCOMES)
    
    update
    
    verifyWritten(VALID_2_OUTCOMES)
  }
  
  it should "update the XML if a file is already present" in {
    createFile(VALID_1_OUTCOMES)
    upload(Full(VALID_2_HTML), VALID_2_OUTCOMES)
    
    update
    
    verifyWritten(VALID_1_OUTCOMES ++ VALID_2_OUTCOMES);
  }
  
  it should "update an existing XML without duplicates" in {
    val anotherOne = Outcome("Club A", "Club B", 2, 2, new LocalDate(2012, 7, 23))
    
    createFile(List(anotherOne, VALID_1_OUTCOMES(0)))
    upload(Full(VALID_1_HTML), VALID_1_OUTCOMES)
    
    update
    
    verifyWritten(anotherOne :: VALID_1_OUTCOMES)
  }
  
  it should "not update the XML if the downloader fails" in {
    createEmptyFile
    upload(Failure("uh-oh"))
    
    update
    
    verifyNothingWritten
  }
  
  val url = mock[Url]
  val fileName = "/some/filename"
  
  private def createEmptyFile =
    when(files importFrom fileName) thenReturn Failure(new FileNotFoundException)
  
  private def createFile(result: List[Outcome]) =
    when(files importFrom fileName) thenReturn Full(result)
  
  private def upload(content: Box[String], result: List[Outcome] = Nil) {
    when(downloader fetch url) thenReturn content
    content.foreach { s => when(parser parse s) thenReturn result }
  }
  
  private def update = updater.update(url, fileName)
  
  private def verifyWritten(xs: List[Outcome]) =
    verify(files).exportTo(fileName, xs)
  
  private def verifyNothingWritten =
    verify(files, never).exportTo(anyString, any(classOf[List[Outcome]]))
}