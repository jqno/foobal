package nl.jqno.foobal.predictoutcomes.functional_rules

import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.predictoutcomes.AverageOfLastMatchesPredicter
import nl.jqno.foobal.test_data.SampleData
import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

@RunWith(classOf[JUnitRunner])
class AverageOfLastMatchesPredicterTest extends FlatSpec with Matchers with OneInstancePerTest {

  var history: List[Outcome] = Nil

  val homeTeam = "NAC"
  val outTeam = "Willem II"
  val date = new LocalDate(2012, 9, 10)

  behavior of """Rule "Average of last 6 matches""""
  
  it should "take the average of the last 6 matches for each team" in {
    history = SampleData.MiniSeason
    assertLatestOutcome(3, 0)
  }
  
  it should "take into account all homeTeam's 6 last matches" in {
    history = Outcome("NAC", "FC Twente", 40, 0, new LocalDate(2012, 9, 20)) :: SampleData.MiniSeason
    assertLatestOutcome(9, 0)
  }
  
  it should "only take into account homeTeam's 6 most recent matches" in {
    history = Outcome("NAC", "FC Twente", 40, 0, new LocalDate(2012, 9, 9)) :: SampleData.MiniSeason
    assertLatestOutcome(3, 0)
  }
  
  it should "take into account all outTeam's 6 last matches" in {
    history = Outcome("FC Twente", "Willem II", 0, 19, new LocalDate(2012, 9, 20)) :: SampleData.MiniSeason
    assertLatestOutcome(3, 3)
  }
  
  it should "only take into account outTeam's 6 most recent matches" in {
    history = Outcome("FC Twente", "Willem II", 0, 19, new LocalDate(2012, 9, 9)) :: SampleData.MiniSeason
    assertLatestOutcome(3, 0)
  }
  
  it should "subtract 'pessimism factor', then round down" in pending
  
  it should "take as much data as is available, if there are no 6 previous matches" in pending
  
  it should "predict a score of 0 if no data is available at all" in pending

  private def assertLatestOutcome(homeScore: Int, outScore: Int) = {
    val actual = AverageOfLastMatchesPredicter.predict(history, homeTeam, outTeam, date)
    actual should be (Outcome(homeTeam, outTeam, homeScore, outScore, date))
  }
}
