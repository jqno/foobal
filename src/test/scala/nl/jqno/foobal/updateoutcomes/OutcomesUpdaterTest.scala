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
import nl.jqno.foobal.io.SampleData.ValidHtml_1
import nl.jqno.foobal.io.SampleData.ValidOutcomes_1
import nl.jqno.foobal.io.SampleData.ValidHtml_2
import nl.jqno.foobal.io.SampleData.ValidOutcomes_2
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
    upload(Full(ValidHtml_1), ValidOutcomes_1)
    
    update
    
    verifyWritten(ValidOutcomes_1)
  }
  
  it should "download other outcomes and update the XML file" in {
    createEmptyFile
    upload(Full(ValidHtml_2), ValidOutcomes_2)
    
    update
    
    verifyWritten(ValidOutcomes_2)
  }
  
  it should "update the XML if a file is already present" in {
    createFile(ValidOutcomes_1)
    upload(Full(ValidHtml_2), ValidOutcomes_2)
    
    update
    
    verifyWritten(ValidOutcomes_1 ++ ValidOutcomes_2);
  }
  
  it should "update an existing XML without duplicates" in {
    val anotherOne = Outcome("Club A", "Club B", 2, 2, new LocalDate(2012, 7, 23))
    
    createFile(List(anotherOne, ValidOutcomes_1(0)))
    upload(Full(ValidHtml_1), ValidOutcomes_1)
    
    update
    
    verifyWritten(anotherOne :: ValidOutcomes_1)
  }
  
  it should "not update the XML if the downloader fails" in {
    createEmptyFile
    upload(Failure("uh-oh"))
    
    update
    
    verifyNothingWritten
  }
  
  private val FileName = "/some/filename"
  private val url = mock[Url]
  
  private def createEmptyFile {
    when (files importFrom FileName) thenReturn Failure(new FileNotFoundException)
  }
  
  private def createFile(result: List[Outcome]) {
    when (files importFrom FileName) thenReturn Full(result)
  }
  
  private def upload(content: Box[String], result: List[Outcome] = Nil) {
    when (downloader fetch url) thenReturn content
    content foreach { s => when (parser parse s) thenReturn result }
  }
  
  private def update {
    updater.update(url, FileName)
  }
  
  private def verifyWritten(xs: List[Outcome]) {
    verify (files).exportTo(FileName, xs)
  }
  
  private def verifyNothingWritten {
    verify (files, never).exportTo(anyString, any(classOf[List[Outcome]]))
  }
}