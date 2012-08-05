package nl.jqno.foobal.predictoutcomes

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

import nl.jqno.foobal.domain.Outcome

@RunWith(classOf[JUnitRunner])
class RulesTest extends FlatSpec with ShouldMatchers {
  behavior of "The Rules"
  
  it should "predict a previous outcome" in {
    val history = List(
      Outcome("NAC", "Willem II", 1, 0, new LocalDate(2011, 9, 10)),
      Outcome("PSV", "Willem II", 2, 0, new LocalDate(2011, 9, 11)),
      Outcome("NAC", "PSV", 0, 1, new LocalDate(2011, 9, 12)))
    
    assertLatestOutcome(history, 1, 0)
  }
  
  it should "select only the most recent outcome" in {
    val history = List(
      Outcome("NAC", "Willem II", 1, 0, new LocalDate(2011, 9, 10)),
      Outcome("NAC", "Willem II", 2, 0, new LocalDate(2010, 9, 10)))
    
    assertLatestOutcome(history, 1, 0)
  }
  
  it should "return 0-0 when there is no previous outcome" in {
    assertLatestOutcome(List(), 0, 0)
  }
  
  private def assertLatestOutcome(history: List[Outcome], homeScore: Int, outScore: Int) = {
    val p = new DroolsPredicter("drl/rules.drl")
    
    val result = p.predict(history, "NAC", "Willem II", new LocalDate(2012, 8, 20))
    
    result should be (Outcome("NAC", "Willem II", homeScore, outScore, new LocalDate(2012, 8, 20)))
  }
}