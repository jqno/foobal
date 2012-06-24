package nl.jqno.foobal.io

import java.io.IOException
import java.io.InputStream
import java.net.URL

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