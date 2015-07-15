package nl.jqno.foobal

import nl.jqno.foobal.io.{DateFactory, Files, Url}
import nl.jqno.foobal.predictoutcomes._
import nl.jqno.foobal.updateoutcomes.OutcomesUpdater

import scala.util.{Failure, Success}

class Main(
    clock: DateFactory = new DateFactory,
    files: Files = new Files,
    updater: OutcomesUpdater = new OutcomesUpdater,
    predicter: Predicter = Main.aggregatePredicter) {
  
  def start(args: Array[String]): String = args match {
    case Array("update", url, file) =>
      updater.update(new Url(url), file)
      Main.OkText
    case Array("predict", file, homeTeam, outTeam) =>
      files.importFrom(file) match {
        case Success(history) => predicter.predict(history, homeTeam, outTeam, clock.today).toString
        case Failure(f) => s"${Main.ExceptionOccurred}\n${f.getMessage}"
      }
    case _ => Main.HelpText
  }
}

object Main {
  val aggregatePredicter = new AggregatePredicter(Vector(
    AverageOfLastMatchesPredicter, DistanceOnLeaderboardPredicter, LastYearPredicter
  ))
  val Files = List("last_year", "average_of_last_6_matches", "distance_on_leaderboard") map { "drl/" + _ + ".drl" }
  
  val OkText = "OK"
  val ExceptionOccurred = "A problem occurred!"
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