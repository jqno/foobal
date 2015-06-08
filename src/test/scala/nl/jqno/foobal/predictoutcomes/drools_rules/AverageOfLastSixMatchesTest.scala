package nl.jqno.foobal.predictoutcomes.drools_rules

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.OneInstancePerTest
import org.scalatest.junit.JUnitRunner

import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.test_data.SampleData

@RunWith(classOf[JUnitRunner])
class AverageOfLastSixMatchesTest extends RuleTester with OneInstancePerTest {
  override val FileName = "average_of_last_6_matches.drl"
  
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
}
