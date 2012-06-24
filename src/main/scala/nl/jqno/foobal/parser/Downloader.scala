package nl.jqno.foobal.parser

import java.io.IOException

import scala.io.Source

import com.nummulus.boite._

class Downloader(val url: Url) {
  private val CONNECT_TIMEOUT = 3000
  private val READ_TIMEOUT = 3000
  
  def fetch: Box[String] = {
    val con = url.openConnection
    con.setConnectTimeout(CONNECT_TIMEOUT)
    con.setReadTimeout(READ_TIMEOUT)
    
    Box.wrap {
      val stream = Source.fromInputStream(con.getInputStream)
      stream.getLines.mkString("\n")
    }
  }
}