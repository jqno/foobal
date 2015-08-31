package nl.jqno.foobal.updateoutcomes

import nl.jqno.foobal.io.{Downloader, Files, HtmlParser, Url}

import scala.util.Try

class OutcomesUpdater(
    downloader: Downloader = new Downloader,
    parser: HtmlParser = new HtmlParser,
    files: Files = new Files) {
  
  def update(url: Url, fileName: String): Try[Unit] = {
    val existing = files.importFrom(fileName).getOrElse(Nil)
    
    downloader.fetch(url).map { html =>
      val incoming = parser.parse(html)
      val outcomes = (existing ++ incoming).distinct
      println(s"New outcomes: ${outcomes.size - existing.size}")
      files.exportTo(fileName, outcomes)
    }
  }
}