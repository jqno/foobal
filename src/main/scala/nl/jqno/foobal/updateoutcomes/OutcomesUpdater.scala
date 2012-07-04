package nl.jqno.foobal.updateoutcomes

import nl.jqno.foobal.io.Downloader
import nl.jqno.foobal.io.Files
import nl.jqno.foobal.io.HtmlParser
import nl.jqno.foobal.io.DateFactory

class OutcomesUpdater(downloader: Downloader, parser: HtmlParser, files: Files) {
  def update(fileName: String): Unit = {
    downloader.fetch foreach { html =>
      val outcomes = parser.parse(html)
      files.exportTo(fileName, outcomes)
    }
  }
}