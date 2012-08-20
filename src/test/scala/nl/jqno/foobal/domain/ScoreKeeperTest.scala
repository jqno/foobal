package nl.jqno.foobal.domain

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.OneInstancePerTest
import org.scalatest.mock.MockitoSugar
import nl.jqno.foobal.io.Random
import org.mockito.Mockito._
import org.joda.time.LocalDate

@RunWith(classOf[JUnitRunner])
class ScoreKeeperTest extends FlatSpec with ShouldMatchers with MockitoSugar with OneInstancePerTest {
  val HomeTeam = "NAC"
  val OutTeam = "Willem II"
  val Date = LocalDate.now
  
  val random = mock[Random]
  val keeper = new ScoreKeeper(HomeTeam, OutTeam, Date, random)
  
  
  behavior of "A ScoreKeeper"
  
  it should "have certain fields for the rules to work" in {
    keeper.homeTeam should be (HomeTeam)
    keeper.outTeam should be (OutTeam)
    keeper.date should be (Date)
  }
  
  it should "guess the one score added to it" in {
    keeper.add(0, 2)
    assertScore(keeper.guess, 0, 2)
  }
  
  it should "guess a random score if multiple scores are added to it" in {
    keeper.add(0, 0)
    keeper.add(1, 0)
    keeper.add(0, 2)
    
    when (random.nextInt(3)) thenReturn (1)
    assertScore(keeper.guess, 1, 0)
    
    when (random.nextInt(3)) thenReturn (0)
    assertScore(keeper.guess, 0, 0)
  }
  
  it should "throw an exception when no score was added" in {
    evaluating { keeper.guess } should produce [IllegalStateException]
  }
  
  private def assertScore(outcome: Outcome, homeScore: Int, outScore: Int) {
    outcome should be (Outcome(HomeTeam, OutTeam, homeScore, outScore, Date))
  }
}
