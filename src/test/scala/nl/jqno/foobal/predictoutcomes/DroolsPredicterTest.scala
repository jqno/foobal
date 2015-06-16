package nl.jqno.foobal.predictoutcomes

import org.drools.runtime.rule.ConsequenceException
import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

import nl.jqno.foobal.domain.ScoreKeeper
import nl.jqno.foobal.test_data.SampleData._

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
    val out = p.predict(ValidOutcomes_2, result.homeTeam, result.outTeam, result.date)
    out should be (result)
  }
  
  it should "execute multiple files" in {
    val (home, out, date) = ("home", "out", new LocalDate(2015, 6, 16))
    val scoreKeeper = new ScoreKeeper(home, out, date)
    val files = (1 to 3) map { "drl/multiple_files/" + _ + ".drl" }
    val p = new DroolsPredicter(files.toList, Some(scoreKeeper))
    
    p.predict(List(), home, out, date)

    val scores = scoreKeeper.theScores
    scores should be ((3, 3) :: (2, 2) :: (1, 1) :: Nil)
  }
}