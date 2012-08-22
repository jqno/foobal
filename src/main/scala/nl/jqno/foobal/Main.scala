package nl.jqno.foobal

import com.nummulus.boite.Full

import nl.jqno.foobal.io.DateFactory
import nl.jqno.foobal.io.Files
import nl.jqno.foobal.io.Url
import nl.jqno.foobal.predictoutcomes.DroolsPredicter
import nl.jqno.foobal.predictoutcomes.Predicter
import nl.jqno.foobal.updateoutcomes.OutcomesUpdater

class Main(
    clock: DateFactory = new DateFactory,
    files: Files = new Files,
    updater: OutcomesUpdater = new OutcomesUpdater,
    predicter: Predicter = new DroolsPredicter(Main.Files)) {
  
  def start(args: Array[String]): String = args match {
    case Array("update", url, file) => {
      updater.update(new Url(url), file)
      Main.OkText
    }
    case Array("predict", file, homeTeam, outTeam) => {
      files.importFrom(file) match {
        case Full(history) => predicter predict (history, homeTeam, outTeam, clock.today) toString
        case _ => Main.FileNotFoundText
      }
    }
    case _ => Main.HelpText
  }
}

object Main {
  val Files = List("last_year", "average_of_last_6_matches") map { "drl/" + _ + ".drl" }
  
  val OkText = "OK"
  val FileNotFoundText = "Could not find file!"
  val HelpText = {
    val s = """foobal.sh
      |  update <url> <file>
      |  predict <file> "<homeTeam>" "<outTeam>"
      |"""
    s.stripMargin
  }
  
  def main(args: Array[String]) {
    println(new Main().start(args))
  }
}