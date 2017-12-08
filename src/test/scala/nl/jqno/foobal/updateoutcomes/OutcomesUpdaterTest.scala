package nl.jqno.foobal.updateoutcomes

import java.io.FileNotFoundException

import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.io.{Downloader, Files, HtmlParser, Url}
import nl.jqno.foobal.test_data.SampleData._
import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.mockito.Matchers.{any, anyString}
import org.mockito.Mockito.{never, verify, when}
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

import scala.util.{Failure, Success, Try}

@RunWith(classOf[JUnitRunner])
class OutcomesUpdaterTest extends FlatSpec with Matchers with OneInstancePerTest with MockitoSugar {
  val files = mock[Files]
  val parser = mock[HtmlParser]
  val downloader = mock[Downloader]
  val updater = new OutcomesUpdater(downloader, parser, files)
  
  
  behavior of "An OutcomesUpdater"
  
  it should "download outcomes and update the XML file" in {
    createEmptyFile()
    upload(Success(validHtml1), validOutcomes1)
    
    update()
    
    verifyWritten(validOutcomes1)
  }
  
  it should "download other outcomes and update the XML file" in {
    createEmptyFile()
    upload(Success(validHtml2), validOutcomes2)
    
    update()
    
    verifyWritten(validOutcomes2)
  }
  
  it should "update the XML if a file is already present" in {
    createFile(validOutcomes1)
    upload(Success(validHtml2), validOutcomes2)
    
    update()
    
    verifyWritten(validOutcomes1 ++ validOutcomes2)
  }
  
  it should "update an existing XML without duplicates" in {
    val anotherOne = Outcome("Club A", "Club B", 2, 2, new LocalDate(2012, 7, 23))
    
    createFile(List(anotherOne, validOutcomes1(0)))
    upload(Success(validHtml1), validOutcomes1)
    
    update()
    
    verifyWritten(anotherOne :: validOutcomes1)
  }
  
  it should "not update the XML if the downloader fails" in {
    createEmptyFile()
    upload(Failure(new FileNotFoundException))
    
    val result = update()
    
    verifyNothingWritten()
    result match {
      case Failure(e) => e.getClass should be (classOf[FileNotFoundException])
      case _ => fail()
    }
  }
  
  val someFileName = "/some/filename"
  val url = mock[Url]
  
  def createEmptyFile(): Unit =
    when (files importFrom someFileName) thenReturn Failure(new FileNotFoundException)
  
  def createFile(result: List[Outcome]): Unit =
    when (files importFrom someFileName) thenReturn Success(result)
  
  def upload(content: Try[String], result: List[Outcome] = Nil): Unit = {
    when (downloader fetch url) thenReturn content
    content foreach { s => when (parser parse s) thenReturn result }
  }
  
  def update(): Try[Unit] =
    updater.update(url, someFileName)
  
  def verifyWritten(xs: List[Outcome]): Unit =
    verify (files).exportTo(someFileName, xs)
  
  def verifyNothingWritten(): Unit =
    verify (files, never).exportTo(anyString, any(classOf[List[Outcome]]))
}