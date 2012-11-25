package nl.jqno.foobal.predictoutcomes.rules

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import nl.jqno.foobal.test_data.SampleData

@RunWith(classOf[JUnitRunner])
class DistanceOnLeaderboardTest extends RuleTester {
  override val FileName = "distance_on_leaderboard.drl"
  
  
  behavior of """Rule "Distance on leaderboard""""
  
  it should "" in {
    history = SampleData.MiniSeason
  }
}