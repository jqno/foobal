package nl.jqno.foobal.domain

import com.nummulus.boite._
import org.joda.time.format.DateTimeFormat
import org.joda.time.LocalDate

case class Outcome(
    homeTeam: String,
    outTeam: String,
    homeScore: Int,
    outScore: Int,
    date: LocalDate
)
{
  def toXml =
    <outcome>
      <homeTeam>{homeTeam}</homeTeam>
      <outTeam>{outTeam}</outTeam>
      <homeScore>{homeScore.toString}</homeScore>
      <outScore>{outScore.toString}</outScore>
      <date>{date.toString(DateTimeFormat.forPattern("YYYY-MM-dd"))}</date>
    </outcome>
}

object Outcome {
  def apply(xml: scala.xml.Node): Box[Outcome] = Box.wrap {
    val outcome = xml \\ "outcome"
    val homeTeam  = (outcome \\ "homeTeam").text
    val outTeam   = (outcome \\ "outTeam").text
    val homeScore = (outcome \\ "homeScore").text.toInt
    val outScore  = (outcome \\ "outScore").text.toInt
    val date      = new LocalDate((outcome \\ "date").text)
    
    Outcome(homeTeam, outTeam, homeScore, outScore, date)
  }
}