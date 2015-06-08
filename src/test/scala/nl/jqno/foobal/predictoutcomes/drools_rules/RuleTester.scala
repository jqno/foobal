package nl.jqno.foobal.predictoutcomes.drools_rules

import org.joda.time.LocalDate
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.predictoutcomes.DroolsPredicter
import org.scalatest.OneInstancePerTest

trait RuleTester extends FlatSpec with ShouldMatchers with OneInstancePerTest {
  val FileName: String
  
  private val Date = new LocalDate(2012, 9, 10)
  
  private var _history: List[Outcome] = List()
  protected[this] def history = _history
  
  def history_=(h: List[Outcome]): Unit =
    _history = h
  
  private var _teams: (String, String) = ("NAC", "Willem II")
  protected[this] def teams = _teams
  
  def teams_=(t: (String, String)): Unit =
    _teams = t
  
  def assertLatestOutcome(homeScore: Int, outScore: Int): Unit = {
    val (home, out) = teams
    val p = new DroolsPredicter(List("drl/" + FileName))
    val result = p.predict(history, home, out, Date)
    
    result should be (Outcome(home, out, homeScore, outScore, Date))
  }
}