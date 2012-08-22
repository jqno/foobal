package nl.jqno.foobal.predictoutcomes.rules

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
  
  def history_=(h: List[Outcome]) {
    _history = h
  }
  
  def assertLatestOutcome(homeScore: Int, outScore: Int) = {
    val p = new DroolsPredicter(List("drl/" + FileName))
    val result = p.predict(history, "NAC", "Willem II", Date)
    
    result should be (Outcome("NAC", "Willem II", homeScore, outScore, Date))
  }
}