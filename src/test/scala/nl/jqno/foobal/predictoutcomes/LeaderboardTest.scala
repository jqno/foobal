package nl.jqno.foobal.predictoutcomes

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

import nl.jqno.foobal.domain.Ranking
import nl.jqno.foobal.test_data.SampleData

@RunWith(classOf[JUnitRunner])
class LeaderboardTest extends FlatSpec with ShouldMatchers {
  behavior of "Leaderboard"
  
  it should "return an empty list on empty data" in {
    Leaderboard(null, Nil) should be (Nil)
  }
  
  it should "return an ordered list of WINNERS" in {
    val date = new LocalDate(2011, 10, 1)
    val expected = Ranking("NAC", 0) :: Ranking("Ajax", 1) :: Ranking("PSV", 2) :: Ranking("Willem II", 3) :: Nil
    Leaderboard(date, SampleData.MiniSeason) should be (expected)
  }
  
  it should "ignore last season's outcomes" in {
    val date = new LocalDate(2012, 10, 1)
    Leaderboard(date, SampleData.MiniSeason) should be (Nil)
  }
  
  it should "ignore next season's outcomes" in {
    val date = new LocalDate(2010, 10, 1)
    Leaderboard(date, SampleData.MiniSeason) should be (Nil)
  }
  
  it should "use total goals when points are equal" in pending
}