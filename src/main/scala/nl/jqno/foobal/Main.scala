package nl.jqno.foobal

import java.io.{PrintWriter, StringWriter}

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
      updater.update(new Url(url), file) match {
        case Success(_) => Main.okText
        case Failure(f) => Main.stackTraceToString(f)
      }
    case Array("predict", file, homeTeam, outTeam) =>
      files.importFrom(file) match {
        case Success(history) =>
          val o = predicter.predict(history, homeTeam, outTeam, clock.today)
          s"Prediction: ${o.homeTeam} ${o.homeScore} - ${o.outScore} ${o.outTeam}"
        case Failure(f) => Main.stackTraceToString(f)
      }
    case _ => Main.helpText
  }
}

object Main {
  val aggregatePredicter = new AggregatePredicter(Vector(
    AverageOfLastMatchesPredicter, DistanceOnLeaderboardPredicter, LastYearPredicter
  ))
  
  val okText = "OK"
  val helpText = {
    val s = """foobal.sh
      |  update <url> <file>
      |  predict <file> "<homeTeam>" "<outTeam>"
      |"""
    s.stripMargin
  }

  val exceptionOccurred = "A problem occurred!"
  def stackTraceToString(e: Throwable) = {
    val sw = new StringWriter
    val pw = new PrintWriter(sw)
    e.printStackTrace(pw)
    s"$exceptionOccurred\n${sw.toString}"
  }
  
  def main(args: Array[String]) {
    println(new Main().start(args))
  }
}