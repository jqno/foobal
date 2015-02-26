package nl.jqno.foobal.updateoutcomes

import java.io.FileNotFoundException
import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.mockito.Matchers.any
import org.mockito.Matchers.anyString
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.when
import org.scalatest.FlatSpec
import org.scalatest.OneInstancePerTest
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.io.Downloader
import nl.jqno.foobal.io.Files
import nl.jqno.foobal.io.HtmlParser
import nl.jqno.foobal.io.Url
import nl.jqno.foobal.test_data.SampleData._
import scala.util.Try
import scala.util.Failure
import scala.util.Success

@RunWith(classOf[JUnitRunner])
class OutcomesUpdaterTest extends FlatSpec with ShouldMatchers with OneInstancePerTest with MockitoSugar {
  val files = mock[Files]
  val parser = mock[HtmlParser]
  val downloader = mock[Downloader]
  val updater = new OutcomesUpdater(downloader, parser, files)
  
  
  behavior of "An OutcomesUpdater"
  
  it should "download outcomes and update the XML file" in {
    createEmptyFile()
    upload(Success(ValidHtml_1), ValidOutcomes_1)
    
    update()
    
    verifyWritten(ValidOutcomes_1)
  }
  
  it should "download other outcomes and update the XML file" in {
    createEmptyFile()
    upload(Success(ValidHtml_2), ValidOutcomes_2)
    
    update()
    
    verifyWritten(ValidOutcomes_2)
  }
  
  it should "update the XML if a file is already present" in {
    createFile(ValidOutcomes_1)
    upload(Success(ValidHtml_2), ValidOutcomes_2)
    
    update()
    
    verifyWritten(ValidOutcomes_1 ++ ValidOutcomes_2)
  }
  
  it should "update an existing XML without duplicates" in {
    val anotherOne = Outcome("Club A", "Club B", 2, 2, new LocalDate(2012, 7, 23))
    
    createFile(List(anotherOne, ValidOutcomes_1(0)))
    upload(Success(ValidHtml_1), ValidOutcomes_1)
    
    update()
    
    verifyWritten(anotherOne :: ValidOutcomes_1)
  }
  
  it should "not update the XML if the downloader fails" in {
    createEmptyFile
    upload(Failure(new FileNotFoundException))
    
    update()
    
    verifyNothingWritten()
  }
  
  val FileName = "/some/filename"
  val url = mock[Url]
  
  def createEmptyFile(): Unit =
    when (files importFrom FileName) thenReturn Failure(new FileNotFoundException)
  
  def createFile(result: List[Outcome]): Unit =
    when (files importFrom FileName) thenReturn Success(result)
  
  def upload(content: Try[String], result: List[Outcome] = Nil): Unit = {
    when (downloader fetch url) thenReturn content
    content foreach { s => when (parser parse s) thenReturn result }
  }
  
  def update(): Unit =
    updater.update(url, FileName)
  
  def verifyWritten(xs: List[Outcome]): Unit =
    verify (files).exportTo(FileName, xs)
  
  def verifyNothingWritten(): Unit =
    verify (files, never).exportTo(anyString, any(classOf[List[Outcome]]))
}