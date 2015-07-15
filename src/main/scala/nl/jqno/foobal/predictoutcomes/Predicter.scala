package nl.jqno.foobal.predictoutcomes

import nl.jqno.foobal.domain.Outcome
import org.joda.time.LocalDate

trait Predicter {
  def predict(history: List[Outcome], homeTeam: String, outTeam: String, date: LocalDate): Outcome
}