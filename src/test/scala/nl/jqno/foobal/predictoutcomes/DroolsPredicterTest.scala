package nl.jqno.foobal.predictoutcomes

import java.lang.IllegalStateException

import org.drools.runtime.rule.ConsequenceException
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

import com.nummulus.boite.Full

import nl.jqno.foobal.domain.ScoreKeeper
import nl.jqno.foobal.io.SampleData.ValidOutcomes_2

@RunWith(classOf[JUnitRunner])
class DroolsPredicterTest extends FlatSpec with ShouldMatchers with MockitoSugar {
  behavior of "A DroolsPredicter"
  
  it should "round-trip through the rule engine" in {
    val p = new DroolsPredicter(List("drl/fail.drl"))
    val ex = evaluating { p.predict(List(), "", "", null) } should produce [ConsequenceException]
    ex.getCause.getClass should be (classOf[IllegalStateException])
  }
  
  it should "generate output based on input" in {
    val result = ValidOutcomes_2(0)
    val p = new DroolsPredicter(List("drl/nac-finder.drl"))
    val out = p.predict(ValidOutcomes_2, result.homeTeam, result.outTeam, result.date);
    out should be (result)
  }
  
  it should "execute multiple files" in {
    val scoreKeeper = mock[ScoreKeeper]
    val files = (1 to 3) map { "drl/multiple_files/" + _ + ".drl" } toList
    val p = new DroolsPredicter(files, Full(scoreKeeper))
    
    p.predict(List(), "", "", null)
    
    verify (scoreKeeper).add(1, 1)
    verify (scoreKeeper).add(2, 2)
    verify (scoreKeeper).add(3, 3)
  }
}