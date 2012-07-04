package nl.jqno.foobal.updateoutcomes

import nl.jqno.foobal.io.Downloader
import nl.jqno.foobal.io.Files
import nl.jqno.foobal.io.HtmlParser

class OutcomesUpdater(downloader: Downloader, files: Files) {
  def update(fileName: String, seasonEndYear: Int): Unit = {
    downloader.fetch foreach { html =>
      val parser = new HtmlParser
      val outcomes = parser.parse(html, seasonEndYear)
      files.exportTo(fileName, outcomes)
    }
  }
}