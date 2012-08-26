package nl.jqno.foobal.predictoutcomes.rules

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import nl.jqno.foobal.domain.Outcome
import org.joda.time.LocalDate
import org.scalatest.OneInstancePerTest

@RunWith(classOf[JUnitRunner])
class AverageOfLastSixMatchesTest extends RuleTester with OneInstancePerTest {
  override val FileName = "average_of_last_6_matches.drl"
  
  private val History =
        Outcome("NAC",       "Ajax",      4, 0, new LocalDate(2011, 9, 10)) ::
        Outcome("Ajax",      "NAC",       0, 0, new LocalDate(2011, 9, 11)) ::
        Outcome("Willem II", "PSV",       0, 1, new LocalDate(2011, 9, 12)) ::
        Outcome("PSV",       "Willem II", 1, 1, new LocalDate(2011, 9, 13)) ::
        Outcome("NAC",       "Willem II", 8, 2, new LocalDate(2011, 9, 14)) ::
        Outcome("Willem II", "NAC",       1, 4, new LocalDate(2011, 9, 15)) ::
        Outcome("Ajax",      "Willem II", 4, 0, new LocalDate(2011, 9, 16)) ::
        Outcome("Willem II", "Ajax",      2, 2, new LocalDate(2011, 9, 17)) ::
        Outcome("NAC",       "PSV",       2, 1, new LocalDate(2011, 9, 18)) ::
        Outcome("PSV",       "NAC",       1, 6, new LocalDate(2011, 9, 19)) ::
        Nil 
  
  behavior of """Rule "Average of last 6 matches""""
  
  it should "take the average of the last 6 matches for each team" in {
    history = History
    assertLatestOutcome(4, 1)
  }
  
  it should "take into account all homeTeam's 6 last matches" in {
    history = Outcome("NAC", "FC Twente", 40, 0, new LocalDate(2011, 9, 20)) :: History
    assertLatestOutcome(10, 1)
  }
  
  it should "only take into account homeTeam's 6 most recent matches" in {
    history = Outcome("NAC", "FC Twente", 40, 0, new LocalDate(2011, 9, 9)) :: History
    assertLatestOutcome(4, 1)
  }
  
  it should "take into account all outTeam's 6 last matches" in {
    history = Outcome("FC Twente", "Willem II", 0, 19, new LocalDate(2011, 9, 20)) :: History
    assertLatestOutcome(4, 4)
  }
  
  it should "only take into account outTeam's 6 most recent matches" in {
    history = Outcome("FC Twente", "Willem II", 0, 19, new LocalDate(2011, 9, 9)) :: History
    assertLatestOutcome(4, 1)
  }
  
  it should "round averaged scores up" in pending
  
  it should "take as much data as is available, if there are no 6 previous matches" in pending
  
  it should "predict a score of 0 if no data is available at all" in pending
}
