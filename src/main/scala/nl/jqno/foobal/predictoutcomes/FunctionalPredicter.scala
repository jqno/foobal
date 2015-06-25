package nl.jqno.foobal.predictoutcomes

import nl.jqno.foobal.domain.Outcome
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
