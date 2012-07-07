package nl.jqno.foobal

import nl.jqno.foobal.io.Downloader
import nl.jqno.foobal.io.Url
import nl.jqno.foobal.updateoutcomes.OutcomesUpdater

object Main {
  val URL = "http://www.fcupdate.nl/programma-uitslagen/s724/nederland-eredivisie-2011-2012/"
  val FILE_NAME = "/tmp/outcomes.xml"

  def main(args: Array[String]) {
    val downloader = new Downloader(new Url(URL))
    val updater = new OutcomesUpdater(downloader)
    updater.update(FILE_NAME)
  }
}