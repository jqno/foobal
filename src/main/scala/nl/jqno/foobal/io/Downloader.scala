package nl.jqno.foobal.io

import scala.io.Source
import scala.util.Try

class Downloader {
  private val connectTimeout = 3000
  private val readTimeout = 3000
  
  def fetch(url: Url): Try[String] = Try {
    val con = createConnection(url)
    val stream = Source.fromInputStream(con.getInputStream)
    stream.getLines().mkString("\n")
  }
  
  private def createConnection(url: Url) = {
    val con = url.openConnection
    con.setConnectTimeout(connectTimeout)
    con.setReadTimeout(readTimeout)
    con
  }
}