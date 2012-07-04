package nl.jqno.foobal.io

import java.io.FileNotFoundException
import java.io.IOException

import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatest.FlatSpec
import org.scalatest.OneInstancePerTest

import com.nummulus.boite.scalatest.BoiteMatchers._

import SampleData._

@RunWith(classOf[JUnitRunner])
class FilesTest extends FlatSpec with ShouldMatchers with OneInstancePerTest with MockitoSugar {
  val xml = mock[Xml]
  val files = new Files(xml)
  val someFile = ""
  
  behavior of "Files.importFrom"
  
  it should "import the contents of a valid file" in {
    writeToFile(VALID_1_XML)
    files.importFrom(someFile) should be a (full containing VALID_1_OUTCOMES)
  }
  
  it should "import the contents of another valid file" in {
    writeToFile(VALID_2_XML)
    files.importFrom(someFile) should be a (full containing VALID_2_OUTCOMES)
  }
  
  it should "not import the contents of an invalid file" in {
    writeToFile(<wrong>Invalid</wrong>)
    files.importFrom(someFile) should be (empty)
  }
  
  it should "result in a Failure if the file could not be found" in {
    val nonExistingFile = "does not exist"
    when(xml.loadFile(nonExistingFile)) thenThrow (new FileNotFoundException)
    files.importFrom(nonExistingFile) should be a (failure containing classOf[FileNotFoundException])
  }
  
  private def writeToFile(content: scala.xml.Node) = 
    when(xml.loadFile(someFile)) thenReturn content
  
  
  behavior of "File.exportTo"
  
  it should "export XML data to a file" in {
    files.exportTo(someFile, VALID_1_OUTCOMES)
    verify(xml).saveFile(someFile, VALID_1_XML)
  }
  
  it should "export other XML data to a file" in {
    files.exportTo(someFile, VALID_2_OUTCOMES)
    verify(xml).saveFile(someFile, VALID_2_XML)
  }
  
  it should "throw an IOException if the file could not be written to" in {
    val failingFile = "will fail"
    when(xml.saveFile(failingFile, VALID_1_XML)) thenThrow (new IOException)
    intercept[IOException] {
      files.exportTo(failingFile, VALID_1_OUTCOMES)
    }
  }
}
