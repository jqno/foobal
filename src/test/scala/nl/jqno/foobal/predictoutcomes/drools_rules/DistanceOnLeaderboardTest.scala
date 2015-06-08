package nl.jqno.foobal.predictoutcomes.drools_rules

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.test_data.SampleData

@RunWith(classOf[JUnitRunner])
class DistanceOnLeaderboardTest extends RuleTester {
  override val FileName = "distance_on_leaderboard.drl"
  
  
  behavior of """Rule "Distance on leaderboard""""
  
  it should "predict 0-0 for teams with distance of 1 in leaderboard" in {
    history = SampleData.MiniSeason
    teams = ("Ajax", "PSV")
    assertLatestOutcome(0, 0)
  }
  
  it should "predict 0-0 for teams with distance of 1; but reversed from previous test" in {
    history = SampleData.MiniSeason
    teams = ("PSV", "Ajax")
    assertLatestOutcome(0, 0)
  }
  
  it should "predict distance/2 for stronger team, 0 for weaker team (odd, close)" in {
    history = SampleData.MiniSeason
    teams = ("Willem II", "NAC")
    assertLatestOutcome(0, 1)
  }
  
  it should "predict distance/2 for stronger team, 0 for weaker team (even, far)" in {
    val draws = (1 to 15) map { n => Outcome("NAC", n.toString, 0, 0, new LocalDate(2012, 11, n)) }
    val win = Outcome("NAC", "16", 1, 0, new LocalDate(2012, 11, 16))
    
    history = win :: draws.toList
    teams = ("NAC", "16")
    
    history should have size 16
    assertLatestOutcome(8, 0)
  }
}