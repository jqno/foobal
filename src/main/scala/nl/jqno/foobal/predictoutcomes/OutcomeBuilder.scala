package nl.jqno.foobal.predictoutcomes

import org.joda.time.LocalDate

import com.nummulus.boite.Box
import com.nummulus.boite.Empty
import com.nummulus.boite.Full

import nl.jqno.foobal.domain.Outcome

class OutcomeBuilder(val homeTeam: String, val outTeam: String, val date: LocalDate) {
  private var home: Box[Int] = Empty
  private var out:  Box[Int] = Empty
  
  def setOutcome(homeScore: Int, outScore: Int) {
    if (home.isDefined || out.isDefined) {
      throw new IllegalStateException("Outcome was already defined!")
    }
    home = Full(homeScore)
    out  = Full(outScore)
  }
  
  def build: Outcome =
    try {
      Outcome(homeTeam, outTeam, home.get, out.get, date)
    }
    catch {
      case e: NoSuchElementException => throw new IllegalStateException(e)
    }
}