package nl.jqno.foobal.io

import java.io.IOException
import java.io.InputStream
import java.net.URL

import scala.xml.XML

class Xml {
  @throws(classOf[IOException])
  def loadFile(fileName: String): scala.xml.Node = XML.loadFile(fileName)
  
  @throws(classOf[IOException])
  def saveFile(fileName: String, node: scala.xml.Node): Unit = XML.save(fileName, node)
}

class Url(val spec: String) {
  private val url = new URL(spec)
  
  def openConnection: UrlConnection = new UrlConnection(url)
}

class UrlConnection(val url: URL) {
  private val con = url.openConnection
  
  def setConnectTimeout(millis: Int) = con.setConnectTimeout(millis)
  
  def setReadTimeout(millis: Int) = con.setReadTimeout(millis)
  
  @throws(classOf[IOException])
  def getInputStream: InputStream = con.getInputStream
}
