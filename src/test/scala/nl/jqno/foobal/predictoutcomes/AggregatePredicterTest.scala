package nl.jqno.foobal.predictoutcomes

import nl.jqno.foobal.domain.Outcome
import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.mockito.Mockito.{verify, when}
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

@RunWith(classOf[JUnitRunner])
class AggregatePredicterTest extends FlatSpec with Matchers with OneInstancePerTest with MockitoSugar {

  val someHistory = Nil
  val someHomeTeam = "NAC"
  val someOutTeam = "Ajax"
  val someDate = new LocalDate(2015, 7, 1)

  val predicters = (1 to 5) map (_ => mock[Predicter])

  behavior of "AggregatePredicter"

  it should "query all its predicters" in {
    val ap = new AggregatePredicter(predicters take 2)
    predict((0,0), (0,0))

    makePrediction(ap)

    verify(predicters(0)).predict(someHistory, someHomeTeam, someOutTeam, someDate)
    verify(predicters(1)).predict(someHistory, someHomeTeam, someOutTeam, someDate)
  }

  it should "average the outcomes of two predicters (and round up)" in {
    val ap = new AggregatePredicter(predicters take 2)
    predict((4,0), (0,3))

    makePrediction(ap) should be (outcome(2, 2))
  }

  it should "take the median of three predicters" in {
    val ap = new AggregatePredicter(predicters take 3)
    predict((2,2), (1,3), (4,0))

    makePrediction(ap) should be (outcome(2, 2))
  }

  it should "take the median of four predicters (and round up)" in {
    val ap = new AggregatePredicter(predicters take 4)
    predict((0,0), (1,3), (4,0), (2,3))

    makePrediction(ap) should be (outcome(2, 2))
  }

  it should "take the median of five predicters" in {
    val ap = new AggregatePredicter(predicters take 5)
    predict((0,0), (1,3), (4,0), (2,3), (2,3))

    makePrediction(ap) should be (outcome(2, 3))
  }

  def predict(predictions: (Int, Int)*): Unit =
    for (((h, o), i) <- predictions.zipWithIndex) {
      when (predicters(i).predict(someHistory, someHomeTeam, someOutTeam, someDate)) thenReturn (outcome(h, o))
    }

  def makePrediction(ap: AggregatePredicter): Outcome =
    ap.predict(someHistory, someHomeTeam, someOutTeam, someDate)

  def outcome(homeScore: Int, outScore: Int): Outcome =
    Outcome(someHomeTeam, someOutTeam, homeScore, outScore, someDate)
}
