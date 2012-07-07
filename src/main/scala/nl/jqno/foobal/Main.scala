package nl.jqno.foobal

import nl.jqno.foobal.io.Downloader
import nl.jqno.foobal.io.Url
import nl.jqno.foobal.io.HtmlParser
import nl.jqno.foobal.io.DateFactory
import nl.jqno.foobal.io.Files
import nl.jqno.foobal.io.Xml
import nl.jqno.foobal.updateoutcomes.OutcomesUpdater

object Main {
  val URL = "http://www.fcupdate.nl/programma-uitslagen/s724/nederland-eredivisie-2011-2012/"
  val FILE_NAME = "/tmp/outcomes.xml"

  def main(args: Array[String]) {
    val downloader = new Downloader(new Url(URL))
    val clock = new DateFactory
    val parser = new HtmlParser(clock)
    val xml = new Xml
    val files = new Files(xml)
    val updater = new OutcomesUpdater(downloader, parser, files)
    updater.update(FILE_NAME)
  }
}