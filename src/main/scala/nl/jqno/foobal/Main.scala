package nl.jqno.foobal

import com.nummulus.boite.Full
import nl.jqno.foobal.io.DateFactory
import nl.jqno.foobal.io.Files
import nl.jqno.foobal.io.Url
import nl.jqno.foobal.predictoutcomes.Predicter
import nl.jqno.foobal.updateoutcomes.OutcomesUpdater
import nl.jqno.foobal.predictoutcomes.NilNilPredicter

class Main(
    clock: DateFactory = new DateFactory,
    files: Files = new Files,
    updater: OutcomesUpdater = new OutcomesUpdater,
    predicter: Predicter = new NilNilPredicter) {
  
  def start(args: Array[String]): String = args match {
    case Array("update", url, file) =>
      updater update (new Url(url), file)
      "OK"
    case Array("predict", file, homeTeam, outTeam) =>
      files.importFrom(file) match {
        case Full(history) => predicter predict (history, homeTeam, outTeam, clock.today) toString
        case _ => Main.FILE_NOT_FOUND_TEXT
      }
    case _ =>
      Main.HELP_TEXT
  }
}

object Main {
  val HELP_TEXT = {
    val s = """foobal.sh
      |  update <url> <file>
      |  predict <file> "<homeTeam>" "<outTeam>"
      |"""
    s.stripMargin
  }
  
  val FILE_NOT_FOUND_TEXT = "Could not find file!"
  
  def main(args: Array[String]) {
    println(new Main().start(args))
  }
}