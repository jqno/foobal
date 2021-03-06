package nl.jqno.foobal.io

import java.io.{IOException, InputStream}
import java.net.{HttpURLConnection, URL}

import org.joda.time.LocalDate

import scala.xml.XML


class Xml {
  @throws(classOf[IOException])
  def loadFile(fileName: String): scala.xml.Node =
    XML.loadFile(fileName)
  
  @throws(classOf[IOException])
  def saveFile(fileName: String, node: scala.xml.Node): Unit =
    XML.save(fileName, node)
}

class Url(private val spec: String) {
  private val url = new URL(spec)
  
  def openConnection: UrlConnection = new UrlConnection(url)
  
  override final def equals(obj: Any): Boolean = obj match {
    case other: Url => spec == other.spec
    case _          => false
  }
  
  override final def hashCode: Int = spec.##
}

class UrlConnection(url: URL) {
  private val connection = url.openConnection()
  
  def setConnectTimeout(millis: Int): Unit =
    connection.setConnectTimeout(millis)
  
  def setReadTimeout(millis: Int): Unit =
    connection.setReadTimeout(millis)
  
  @throws(classOf[IOException])
  def getInputStream: InputStream =
    connection.getInputStream

  def getResponseCode: Int =
    connection.asInstanceOf[HttpURLConnection].getResponseCode
}

class DateFactory {
  def today: LocalDate = new LocalDate
}
