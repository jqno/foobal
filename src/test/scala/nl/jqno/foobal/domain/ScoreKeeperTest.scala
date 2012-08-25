package nl.jqno.foobal.domain

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.OneInstancePerTest
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class ScoreKeeperTest extends FlatSpec with ShouldMatchers with MockitoSugar with OneInstancePerTest {
  val HomeTeam = "NAC"
  val OutTeam = "Willem II"
  val Date = LocalDate.now
  
  val keeper = new ScoreKeeper(HomeTeam, OutTeam, Date)
  
  
  behavior of "A ScoreKeeper"
  
  it should "have certain fields for the rules to work" in {
    keeper.homeTeam should be (HomeTeam)
    keeper.outTeam should be (OutTeam)
    keeper.date should be (Date)
  }
  
  it should "guess the one score added to it" in {
    keeper.add(0, 2)
    assertScore(0, 2)
  }
  
  it should "guess the average score if two scores are added to it" in {
    keeper.add(0, 1)
    keeper.add(2, 1)
    
    assertScore(1, 1)
  }
  
  it should "round up the average of two scores" in {
    keeper.add(0, 1)
    keeper.add(1, 4)
    
    assertScore(1, 3)
  }
  
  it should "take the median value of an odd-length list of scores" in {
    keeper.add(0, 4)
    keeper.add(2, 0)
    keeper.add(10, 1)
    
    assertScore(2, 1)
  }
  
  it should "take the median value of an even-length list of scores (which is the average of the two middle values)" in {
    keeper.add(0, 4)
    keeper.add(2, 0)
    keeper.add(3, 3)
    keeper.add(10, 1)
    
    assertScore(3, 2)
  }
  
  it should "throw an exception when no score was added" in {
    evaluating { keeper.guess } should produce [IllegalStateException]
  }
  
  private def assertScore(homeScore: Int, outScore: Int) {
    keeper.guess should be (Outcome(HomeTeam, OutTeam, homeScore, outScore, Date))
  }
}
