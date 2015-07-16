package nl.jqno.foobal.predictoutcomes.functional_rules

import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.predictoutcomes.DistanceOnLeaderboardPredicter
import nl.jqno.foobal.test_data.SampleData
import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

@RunWith(classOf[JUnitRunner])
class DistanceOnLeaderboardPredicterTest extends FlatSpec with Matchers with OneInstancePerTest {

  val date = new LocalDate(2012, 9, 10)

  behavior of """Rule "Distance on leaderboard""""
  
  it should "predict 0-0 for teams with distance of 1 in leaderboard" in {
    val history = SampleData.miniSeason
    val teams = ("Ajax", "PSV")
    assertLatestOutcome(history, teams, 0, 0)
  }
  
  it should "predict 0-0 for teams with distance of 1; but reversed from previous test" in {
    val history = SampleData.miniSeason
    val teams = ("PSV", "Ajax")
    assertLatestOutcome(history, teams, 0, 0)
  }
  
  it should "predict distance/2 for stronger team, 0 for weaker team (odd, close)" in {
    val history = SampleData.miniSeason
    val teams = ("Willem II", "NAC")
    assertLatestOutcome(history, teams, 0, 1)
  }
  
  it should "predict distance/2 for stronger team, 0 for weaker team (even, far)" in {
    val draws = (1 to 15) map { n => Outcome("NAC", n.toString, 0, 0, new LocalDate(2012, 11, n)) }
    val win = Outcome("NAC", "16", 1, 0, new LocalDate(2012, 11, 16))
    
    val history = win :: draws.toList
    val teams = ("NAC", "16")
    
    history should have size 16
    assertLatestOutcome(history, teams, 8, 0)
  }

  private def assertLatestOutcome(history: List[Outcome], teams: (String, String), homeScore: Int, outScore: Int) = {
    val (homeTeam, outTeam) = teams
    val actual = DistanceOnLeaderboardPredicter.predict(history, homeTeam, outTeam, date)
    actual should be (Outcome(homeTeam, outTeam, homeScore, outScore, date))
  }
}