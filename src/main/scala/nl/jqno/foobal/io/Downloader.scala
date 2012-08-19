package nl.jqno.foobal.io

import java.io.IOException

import scala.io.Source

import com.nummulus.boite._

class Downloader {
  private val ConnectTimeout = 3000
  private val ReadTimeout = 3000
  
  def fetch(url: Url): Box[String] = Box wrap {
    val con = createConnection(url)
    val stream = Source.fromInputStream(con.getInputStream)
    stream.getLines mkString "\n"
  }
  
  private def createConnection(url: Url) = {
    val con = url.openConnection
    con.setConnectTimeout(ConnectTimeout)
    con.setReadTimeout(ReadTimeout)
    con
  }
}