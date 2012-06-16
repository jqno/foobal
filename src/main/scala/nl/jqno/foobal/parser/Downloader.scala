package nl.jqno.foobal.parser

import java.io.IOException
import java.net.SocketTimeoutException

import scala.io.Source

class Downloader(val url: Url) {
  private val CONNECT_TIMEOUT = 3000
  private val READ_TIMEOUT = 3000
  
  def fetch: Option[String] = {
    val con = url.openConnection
    con.setConnectTimeout(CONNECT_TIMEOUT)
    con.setReadTimeout(READ_TIMEOUT)
    
    try {
      val stream = Source.fromInputStream(con.getInputStream)
      Some(stream.getLines.mkString("\n"))
    }
    catch {
      case _: SocketTimeoutException => None
    }
  }
}