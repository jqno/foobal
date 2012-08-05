package nl.jqno.foobal.predictoutcomes

import org.joda.time.LocalDate
import nl.jqno.foobal.domain.Outcome

class NilNilPredicter extends Predicter {
  override def predict(history: List[Outcome], homeTeam: String, outTeam: String, date: LocalDate): Outcome =
    Outcome(homeTeam, outTeam, 0, 0, date)
}