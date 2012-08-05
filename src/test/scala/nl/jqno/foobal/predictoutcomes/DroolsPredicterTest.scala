package nl.jqno.foobal.predictoutcomes

import java.lang.IllegalStateException

import org.drools.runtime.rule.ConsequenceException
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

import nl.jqno.foobal.io.SampleData._

@RunWith(classOf[JUnitRunner])
class DroolsPredicterTest extends FlatSpec with ShouldMatchers {
  behavior of "A DroolsPredicter"
  
  it should "round-trip through the rule engine" in {
    val p = new DroolsPredicter("drl/fail.drl", List())
    val ex = evaluating { p.predict("", "", null) } should produce [ConsequenceException]
    ex.getCause.getClass should be (classOf[IllegalStateException])
  }
  
  it should "generate output based on input" in {
    val result = VALID_2_OUTCOMES(0)
    val p = new DroolsPredicter("drl/nac-finder.drl", VALID_2_OUTCOMES)
    val out = p.predict(result.homeTeam, result.outTeam, result.date);
    out should be (result)
  }
}