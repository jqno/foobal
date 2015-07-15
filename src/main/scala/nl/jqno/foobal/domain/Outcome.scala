package nl.jqno.foobal.domain

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

import scala.util.Try

case class Outcome(
    homeTeam: String,
    outTeam: String,
    homeScore: Int,
    outScore: Int,
    date: LocalDate) {
  
  val millis: Long = date.toDate.getTime
    
  def toXml: scala.xml.Node =
    <outcome>
      <homeTeam>{homeTeam}</homeTeam>
      <outTeam>{outTeam}</outTeam>
      <homeScore>{homeScore.toString}</homeScore>
      <outScore>{outScore.toString}</outScore>
      <date>{date.toString(DateTimeFormat forPattern "YYYY-MM-dd")}</date>
    </outcome>
}

object Outcome {
  def apply(xml: scala.xml.Node): Try[Outcome] = Try {
    val outcome   = xml \\ "outcome"
    val homeTeam  = (outcome \\ "homeTeam").text
    val outTeam   = (outcome \\ "outTeam").text
    val homeScore = (outcome \\ "homeScore").text.toInt
    val outScore  = (outcome \\ "outScore").text.toInt
    val date      = new LocalDate((outcome \\ "date").text)
    
    Outcome(homeTeam, outTeam, homeScore, outScore, date)
  }
}