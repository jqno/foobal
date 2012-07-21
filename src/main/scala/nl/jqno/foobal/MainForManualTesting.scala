package nl.jqno.foobal

import nl.jqno.foobal.io.Downloader
import nl.jqno.foobal.io.Url
import nl.jqno.foobal.updateoutcomes.OutcomesUpdater

object MainForManualTesting {
  val URL = new Url("http://www.fcupdate.nl/programma-uitslagen/s724/nederland-eredivisie-2011-2012/")
  val FILE_NAME = "/tmp/outcomes.xml"

  def main(args: Array[String]) {
    val downloader = new Downloader
    val updater = new OutcomesUpdater(downloader)
    updater.update(URL, FILE_NAME)
  }
}