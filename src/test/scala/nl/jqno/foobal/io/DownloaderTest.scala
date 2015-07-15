package nl.jqno.foobal.io

import java.io.ByteArrayInputStream
import java.net.SocketTimeoutException

import org.junit.runner.RunWith
import org.mockito.Mockito.{verify, when}
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

import scala.util.{Failure, Success}

@RunWith(classOf[JUnitRunner])
class DownloaderTest extends FlatSpec with Matchers with OneInstancePerTest with MockitoSugar {
  val url = mock[Url]
  val con = mock[UrlConnection]
  val downloader = new Downloader
  
  when (url.openConnection) thenReturn con
  
  
  behavior of "A Downloader"
  
  it should "fetch text from a url" in {
    upload("Hello world")
    downloader.fetch(url) should be (Success("Hello world"))
  }
  
  it should "fetch multi-line text from a url" in {
    upload("Hello\nworld")
    downloader.fetch(url) should be (Success("Hello\nworld"))
  }
  
  it should "return None when the connection times out" in {
    timeout()

    val Failure(f) = downloader.fetch(url)
    f.getClass should be (classOf[SocketTimeoutException])

    verify (con).setConnectTimeout(3000)
    verify (con).setReadTimeout(3000)
  }
  
  def upload(s: String): Unit =
    when (con.getInputStream) thenReturn (new ByteArrayInputStream(s.getBytes))
  
  def timeout(): Unit =
    when (con.getInputStream) thenThrow (new SocketTimeoutException)
}