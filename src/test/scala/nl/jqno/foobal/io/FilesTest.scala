package nl.jqno.foobal.io

import java.io.{FileNotFoundException, IOException}

import nl.jqno.foobal.test_data.SampleData._
import org.junit.runner.RunWith
import org.mockito.Mockito.{verify, when}
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

import scala.util.{Failure, Success}

@RunWith(classOf[JUnitRunner])
class FilesTest extends FlatSpec with Matchers with OneInstancePerTest with MockitoSugar {
  val someFile = ""
    
  val xml = mock[Xml]
  val files = new Files(xml)
  
  
  behavior of "Files.importFrom"
  
  it should "import the contents of a valid file" in {
    writeToFile(validXml_1)
    files.importFrom(someFile) should be (Success(validOutcomes_1))
  }
  
  it should "import the contents of another valid file" in {
    writeToFile(validXml_2)
    files.importFrom(someFile) should be (Success(validOutcomes_2))
  }
  
  it should "not import the contents of an invalid file" in {
    writeToFile(<wrong>Invalid</wrong>)
    val Failure(f) = files.importFrom(someFile)
    f.getClass should be (classOf[IllegalStateException])
    f.getMessage should startWith ("No history found")
  }
  
  it should "result in a Failure if the file could not be found" in {
    val nonExistingFile = "does not exist"
    val exception = new FileNotFoundException
    when(xml.loadFile(nonExistingFile)) thenThrow (exception)
    files.importFrom(nonExistingFile) should be (Failure(exception))
  }
  
  def writeToFile(content: scala.xml.Node): Unit =
    when (xml.loadFile(someFile)) thenReturn content
  
  
  behavior of "File.exportTo"
  
  it should "export XML data to a file" in {
    files.exportTo(someFile, validOutcomes_1)
    verify (xml).saveFile(someFile, validXml_1)
  }
  
  it should "export other XML data to a file" in {
    files.exportTo(someFile, validOutcomes_2)
    verify (xml).saveFile(someFile, validXml_2)
  }
  
  it should "throw an IOException if the file could not be written to" in {
    val failingFile = "will fail"
    when (xml.saveFile(failingFile, validXml_1)) thenThrow (new IOException)
    intercept[IOException] {
      files.exportTo(failingFile, validOutcomes_1)
    }
  }
}
