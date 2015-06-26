package nl.jqno.foobal.predictoutcomes

import nl.jqno.foobal.domain.{Ranking, Outcome}
import org.joda.time.LocalDate

object LastYearPredicter extends Predicter {
  override def predict(history: List[Outcome], homeTeam: String, outTeam: String, date: LocalDate): Outcome = {
    val lastYearHome = history filter (_.homeTeam == homeTeam)
    val lastYearOut = history filter (_.outTeam == outTeam)
    val intersect = lastYearHome intersect lastYearOut

    (lastYearHome, lastYearOut) match {
      case (Nil, Nil)                 => Outcome(homeTeam, outTeam, 0, 0, date)
      case (Nil, _)                   => Outcome(homeTeam, outTeam, 0, 1, date)
      case (_, Nil)                   => Outcome(homeTeam, outTeam, 1, 0, date)
      case (_, _) if intersect == Nil => Outcome(homeTeam, outTeam, 0, 0, date)
      case (_, _)                     => intersect.sortWith((a, b) => a.date isAfter b.date).head.copy(date = date)
    }
  }
}

object AverageOfLastMatchesPredicter extends Predicter {
  override def predict(history: List[Outcome], homeTeam: String, outTeam: String, date: LocalDate): Outcome = {
    val homeScore = predictSingleScore(history, homeTeam)
    val outScore = predictSingleScore(history, outTeam)
    Outcome(homeTeam, outTeam, homeScore, outScore, date)
  }

  private def predictSingleScore(history: List[Outcome], team: String) = {
    val total = history
      .filter(o => o.homeTeam == team || o.outTeam == team)
      .sortWith((a, b) => a.date isAfter b.date)
      .take(6)
      .foldLeft(0)((acc, o) => acc + getScore(o, team))
    (total - 3) / 6
  }

  private def getScore(outcome: Outcome, team: String) = outcome match {
    case Outcome(t, _, score, _, _) if t == team => score
    case Outcome(_, t, _, score, _) if t == team => score
    case _ => throw new IllegalStateException("Can't happen")
  }
}

object DistanceOnLeaderboardPredicter extends Predicter {
  override def predict(history: List[Outcome], homeTeam: String, outTeam: String, date: LocalDate): Outcome = {
    val rankings = Leaderboard(date, history)
    
    val homePosition = (rankings find (_.team == homeTeam)).get.position
    val outPosition = (rankings find (_.team == outTeam)).get.position
    val distance = (homePosition - outPosition) / 2

    if (distance > 0)
      Outcome(homeTeam, outTeam, 0, distance, date)
    else
      Outcome(homeTeam, outTeam, -distance, 0, date)
  }
}