package nl.jqno.foobal.predictoutcomes

import nl.jqno.foobal.domain.{DateUtil, Outcome}
import org.joda.time.LocalDate

object LastYearPredicter extends Predicter {
  override def predict(history: List[Outcome], homeTeam: String, outTeam: String, date: LocalDate): Outcome = {
    val lastYearHome = history filter (_.homeTeam == homeTeam) filter (o => isInPreviousSeason(o.date, date))
    val lastYearOut = history filter (_.outTeam == outTeam) filter (o => isInPreviousSeason(o.date, date))
    val intersect = lastYearHome intersect lastYearOut

    (lastYearHome, lastYearOut) match {
      case (Nil, Nil)                 => Outcome(homeTeam, outTeam, 0, 0, date)
      case (Nil, _)                   => Outcome(homeTeam, outTeam, 1, 0, date)
      case (_, Nil)                   => Outcome(homeTeam, outTeam, 0, 1, date)
      case (_, _) if intersect == Nil => Outcome(homeTeam, outTeam, 0, 0, date)
      case (_, _)                     => intersect.sortWith((a, b) => a.date isAfter b.date).head.copy(date = date)
    }
  }
  
  private def isInPreviousSeason(historyDate: LocalDate, currentDate: LocalDate) = {
    (historyDate isBefore DateUtil.determineSeasonStartDateFor(currentDate)) &&
      (historyDate isAfter DateUtil.determineSeasonStartDateFor(currentDate.minusYears(1)))
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
    
    val distance = for {
      home <- rankings find (_.team == homeTeam)
      out <- rankings find (_.team == outTeam)
    } yield (home.position - out.position) / 2

    distance match {
      case Some(d) if d > 0 => Outcome(homeTeam, outTeam, 0, d, date)
      case Some(d) =>          Outcome(homeTeam, outTeam, -d, 0, date)
      case None =>             Outcome(homeTeam, outTeam, 0, 0, date)
    }
  }
}