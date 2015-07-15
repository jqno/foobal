package nl.jqno.foobal.updateoutcomes

import nl.jqno.foobal.io.{Downloader, Files, HtmlParser, Url}

class OutcomesUpdater(
    downloader: Downloader = new Downloader,
    parser: HtmlParser = new HtmlParser,
    files: Files = new Files) {
  
  def update(url: Url, fileName: String): Unit = {
    val existing = files.importFrom(fileName).getOrElse(Nil)
    
    downloader.fetch(url).foreach { html =>
      val incoming = parser.parse(html)
      val outcomes = existing ++ incoming
      files.exportTo(fileName, outcomes.distinct)
    }
  }
}