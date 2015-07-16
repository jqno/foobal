package nl.jqno.foobal.predictoutcomes

import nl.jqno.foobal.domain.DateUtil._
import nl.jqno.foobal.domain.{Outcome, Ranking}
import org.joda.time.LocalDate

object Leaderboard {
  val win  = 3
  val draw = 1
  val lose = 0
  
  def apply(date: LocalDate, history: List[Outcome]): List[Ranking] = {
    val currentSeason = history filter { o => 
      (o.date isAfter determineSeasonStartDateFor(date)) && (o.date isBefore determineSeasonEndDateFor(date))
    }
    
    val totalPoints = currentSeason.foldLeft(Map[String, Int]()) { (acc, outcome) =>
      if (outcome.homeScore > outcome.outScore)
        addToMap(addToMap(acc, outcome.homeTeam, win), outcome.outTeam, lose)
      else if (outcome.homeScore == outcome.outScore)
        addToMap(addToMap(acc, outcome.homeTeam, draw), outcome.outTeam, draw)
      else
        addToMap(addToMap(acc, outcome.homeTeam, lose), outcome.outTeam, win)
    }
    
    val orderedDescending = totalPoints.toList sortBy { case (_, points) => -points }
    val justTheTeams = orderedDescending map { case (team, _) => team }
    val teamsWithRanking = justTheTeams.zipWithIndex
    
    teamsWithRanking map { case (team, position) => Ranking(team, position) }
  }
  
  private def addToMap(m: Map[String, Int], team: String, points: Int): Map[String, Int] =
    if (m contains team)
      m.updated(team, m(team) + points)
    else
      m + (team -> points)
}