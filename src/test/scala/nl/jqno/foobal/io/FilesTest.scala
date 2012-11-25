package nl.jqno.foobal.io

import java.io.FileNotFoundException
import java.io.IOException

import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.Mockito.when
import org.scalatest.FlatSpec
import org.scalatest.OneInstancePerTest
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

import com.nummulus.boite.scalatest.BoiteMatchers._

import nl.jqno.foobal.test_data.SampleData._

@RunWith(classOf[JUnitRunner])
class FilesTest extends FlatSpec with ShouldMatchers with OneInstancePerTest with MockitoSugar {
  val SomeFile = ""
    
  val xml = mock[Xml]
  val files = new Files(xml)
  
  
  behavior of "Files.importFrom"
  
  it should "import the contents of a valid file" in {
    writeToFile(ValidXml_1)
    files.importFrom(SomeFile) should be a (full containing ValidOutcomes_1)
  }
  
  it should "import the contents of another valid file" in {
    writeToFile(ValidXml_2)
    files.importFrom(SomeFile) should be a (full containing ValidOutcomes_2)
  }
  
  it should "not import the contents of an invalid file" in {
    writeToFile(<wrong>Invalid</wrong>)
    files.importFrom(SomeFile) should be (empty)
  }
  
  it should "result in a Failure if the file could not be found" in {
    val nonExistingFile = "does not exist"
    when(xml.loadFile(nonExistingFile)) thenThrow (new FileNotFoundException)
    files.importFrom(nonExistingFile) should be a (failure containing classOf[FileNotFoundException])
  }
  
  private def writeToFile(content: scala.xml.Node) {
    when (xml.loadFile(SomeFile)) thenReturn content
  }
  
  
  behavior of "File.exportTo"
  
  it should "export XML data to a file" in {
    files.exportTo(SomeFile, ValidOutcomes_1)
    verify (xml).saveFile(SomeFile, ValidXml_1)
  }
  
  it should "export other XML data to a file" in {
    files.exportTo(SomeFile, ValidOutcomes_2)
    verify (xml).saveFile(SomeFile, ValidXml_2)
  }
  
  it should "throw an IOException if the file could not be written to" in {
    val failingFile = "will fail"
    when (xml.saveFile(failingFile, ValidXml_1)) thenThrow (new IOException)
    intercept[IOException] {
      files.exportTo(failingFile, ValidOutcomes_1)
    }
  }
}
