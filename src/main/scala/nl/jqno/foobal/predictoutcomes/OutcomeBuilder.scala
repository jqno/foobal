package nl.jqno.foobal.predictoutcomes

import org.joda.time.LocalDate

import com.nummulus.boite.Box
import com.nummulus.boite.Empty
import com.nummulus.boite.Full

import nl.jqno.foobal.domain.Outcome

class OutcomeBuilder(homeTeam: String, outTeam: String, date: LocalDate) {
  var home: Box[Int] = Empty
  var out:  Box[Int] = Empty
  
  def setOutcome(homeScore: Int, outScore: Int) {
    if (home.isDefined || out.isDefined)
      throw new IllegalStateException("Outcome was already defined!")
    home = Full(homeScore)
    out  = Full(outScore)
  }
  
  def build: Outcome = {
    Outcome(homeTeam, outTeam, get(home), get(out), date)
  }
  
  // TODO Remove this method when Box gets a get method.
  private def get(b: Box[Int]) = b match {
    case Full(value) => value
    case _ => throw new IllegalStateException(b + " doesn't have a value")
  }
}