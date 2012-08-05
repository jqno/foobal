package nl.jqno.foobal.predictoutcomes

import org.joda.time.LocalDate

import nl.jqno.foobal.domain.Outcome

trait Predicter {
  def predict(history: List[Outcome], homeTeam: String, outTeam: String, date: LocalDate): Outcome
}