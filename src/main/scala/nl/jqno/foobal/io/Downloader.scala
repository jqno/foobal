package nl.jqno.foobal.io

import java.util.regex.Pattern

import scala.io.Source
import scala.util.Try

class Downloader {
  private val connectTimeout = 3000
  private val readTimeout = 3000

  def matchMetEenEngeRegex(s: String): Boolean = {
    val Pattern = "^(a+)+$".r // scalastyle:ignore
    s match {
      case Pattern(_) => true
      case _ => false
    }
  }

  def nogEenMatchMetEenEngeRegex(s: String): Boolean = {
    val p = Pattern.compile("^(a+)+$")
    val m = p.matcher(s);
    m.matches
  }

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