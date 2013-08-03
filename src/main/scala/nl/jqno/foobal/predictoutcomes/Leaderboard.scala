package nl.jqno.foobal.predictoutcomes

import nl.jqno.foobal.domain.DateUtil._
import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.domain.Ranking
import org.joda.time.LocalDate

object Leaderboard {
  val Win  = 3
  val Draw = 1
  val Lose = 0
  
  def apply(date: LocalDate, history: List[Outcome]): List[Ranking] = {
    val currentSeason = history filter { o => 
      (o.date isAfter determineSeasonStartDateFor(date)) && (o.date isBefore determineSeasonEndDateFor(date))
    }
    
    val totalPoints = currentSeason.foldLeft(Map[String, Int]()) { (acc, outcome) =>
      if (outcome.homeScore > outcome.outScore)
        addToMap(addToMap(acc, outcome.homeTeam, Win), outcome.outTeam, Lose)
      else if (outcome.homeScore == outcome.outScore)
        addToMap(addToMap(acc, outcome.homeTeam, Draw), outcome.outTeam, Draw)
      else
        addToMap(addToMap(acc, outcome.homeTeam, Lose), outcome.outTeam, Win)
    }
    
    val orderedDescending = totalPoints.toList sortBy { case (_, points) => -points }
    val justTheTeams = orderedDescending map { case (team, _) => team }
    val teamsWithRanking = justTheTeams.zipWithIndex
    
    teamsWithRanking map { case (team, position) => Ranking(team, position) }
  }
  
  private def addToMap(m: Map[String, Int], team: String, points: Int): Map[String, Int] =
    if (m contains team)
      m map { case (k, v) => if (k == team) (team -> (m(team) + points)) else (k -> v) }
    else
      m + (team -> points)
}