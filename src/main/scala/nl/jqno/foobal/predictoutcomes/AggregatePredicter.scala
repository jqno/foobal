package nl.jqno.foobal.predictoutcomes

import nl.jqno.foobal.domain.Outcome
import org.joda.time.LocalDate

class AggregatePredicter(predicters: IndexedSeq[Predicter]) extends Predicter {
  override def predict(history: List[Outcome], homeTeam: String, outTeam: String, date: LocalDate): Outcome = {
    val outcomes = predicters map (_.predict(history, homeTeam, outTeam, date))
    val homeScore = median(outcomes.map(_.homeScore).sorted)
    val outScore = median(outcomes.map(_.outScore).sorted)
    Outcome(homeTeam, outTeam, homeScore, outScore, date)
  }

  private def median(xs: IndexedSeq[Int]): Int =
    if (xs.size % 2 == 0)
      evenMedian(xs)
    else
      oddMedian(xs)

  private def evenMedian(xs: IndexedSeq[Int]): Int = {
    val mid = xs.size / 2
    val a: Double = xs(mid - 1)
    val b = xs(mid)
    scala.math.ceil((a + b) / 2).toInt
  }

  private def oddMedian(xs: IndexedSeq[Int]): Int = xs((xs.size - 1) / 2)
}
