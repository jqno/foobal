package nl.jqno.foobal.io

import java.io.IOException
import java.io.ByteArrayInputStream
import java.net.SocketTimeoutException

import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.Mockito.when
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatest.FlatSpec
import org.scalatest.OneInstancePerTest

import com.nummulus.boite.scalatest.BoiteMatchers._

@RunWith(classOf[JUnitRunner])
class DownloaderTest extends FlatSpec with ShouldMatchers with OneInstancePerTest with MockitoSugar {
  val url = mock[Url]
  val con = mock[UrlConnection]
  val downloader = new Downloader
  
  when (url.openConnection) thenReturn con
  
  
  behavior of "A Downloader"
  
  it should "fetch text from a url" in {
    upload("Hello world")
    downloader.fetch(url) should be a (full containing "Hello world")
  }
  
  it should "fetch multi-line text from a url" in {
    upload("Hello\nworld")
    downloader.fetch(url) should be a (full containing "Hello\nworld")
  }
  
  it should "return None when the connection times out" in {
    timeout
    downloader.fetch(url) should be a (failure containing classOf[SocketTimeoutException])
    verify (con).setConnectTimeout(3000)
    verify (con).setReadTimeout(3000)
  }
  
  private def upload(s: String) {
    when (con.getInputStream) thenReturn (new ByteArrayInputStream(s.getBytes))
  }
  
  private def timeout {
    when (con.getInputStream) thenThrow (new SocketTimeoutException)
  }
}