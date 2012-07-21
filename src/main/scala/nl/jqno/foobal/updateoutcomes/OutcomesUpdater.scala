package nl.jqno.foobal.updateoutcomes

import nl.jqno.foobal.io.Downloader
import nl.jqno.foobal.io.Files
import nl.jqno.foobal.io.HtmlParser
import nl.jqno.foobal.io.DateFactory
import nl.jqno.foobal.io.HtmlParser
import nl.jqno.foobal.io.Url

class OutcomesUpdater(
    downloader: Downloader = new Downloader,
    parser: HtmlParser = new HtmlParser,
    files: Files = new Files) {
  
  def update(url: Url, fileName: String): Unit = {
    downloader.fetch(url) foreach { html =>
      val outcomes = parser.parse(html)
      files.exportTo(fileName, outcomes)
    }
  }
}