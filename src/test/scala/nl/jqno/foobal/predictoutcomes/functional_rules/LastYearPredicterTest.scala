package nl.jqno.foobal.predictoutcomes.functional_rules

import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.predictoutcomes.LastYearPredicter
import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

@RunWith(classOf[JUnitRunner])
class LastYearPredicterTest extends FlatSpec with Matchers with OneInstancePerTest {

  val homeTeam = "NAC"
  val outTeam = "Willem II"
  val date = new LocalDate(2012, 9, 10)

  behavior of """Rule "Last year's outcome""""
  
  it should "predict a previous outcome" in {
    val history =
      Outcome("NAC", "Willem II", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("PSV", "Willem II", 2, 0, new LocalDate(2011, 9, 11)) ::
      Outcome("NAC", "PSV", 0, 1, new LocalDate(2011, 9, 12)) ::
      Nil
    
    assertLatestOutcome(history, 1, 0)
  }
  
  it should "select only the most recent outcome" in {
    val history =
      Outcome("NAC", "Willem II", 3, 0, new LocalDate(2009, 9, 10)) ::
      Outcome("NAC", "Willem II", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("NAC", "Willem II", 2, 0, new LocalDate(2010, 9, 10)) ::
      Nil
    
    assertLatestOutcome(history, 1, 0)
  }
  
  it should "return 0-1 when the home team has no matches last year" in {
    val history =
      Outcome("Willem II", "Ajax", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("PSV", "Willem II", 2, 0, new LocalDate(2010, 9, 10)) ::
      Nil
    
    assertLatestOutcome(history, 0, 1)
  }
  
  it should "return 1-0 when the out team has no matches last year" in {
    val history =
      Outcome("NAC", "Ajax", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("PSV", "NAC", 2, 0, new LocalDate(2010, 9, 10)) ::
      Nil
    
    assertLatestOutcome(history, 1, 0)
  }
  
  it should "return 0-0 if both teams have played, but not against each other (which never happens but still)" in {
    val history =
      Outcome("NAC", "Ajax", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("Willem II", "Ajax", 2, 0, new LocalDate(2010, 9, 10)) ::
      Outcome("Ajax", "NAC", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("Ajax", "Willem II", 2, 0, new LocalDate(2010, 9, 10)) ::
      Nil
    
    assertLatestOutcome(history, 0, 0)
  }
  
  it should "return 0-0 when neither team competed last year" in {
    val history =
      Outcome("Feyenoord", "Ajax", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("PSV", "Feyenoord", 2, 0, new LocalDate(2010, 9, 10)) ::
      Nil
    
    assertLatestOutcome(history, 0, 0)
  }

  private def assertLatestOutcome(history: List[Outcome], homeScore: Int, outScore: Int) = {
    val actual = LastYearPredicter.predict(history, homeTeam, outTeam, date)
    actual should be (Outcome(homeTeam, outTeam, homeScore, outScore, date))
  }
}