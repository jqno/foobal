package nl.jqno.foobal.predictoutcomes.rules

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import nl.jqno.foobal.domain.Outcome

@RunWith(classOf[JUnitRunner])
class LastYearRuleTest extends RuleTester {
  override val FileName = "last_year.drl"
  
  
  behavior of """Rule "Last year's outcome""""
  
  it should "predict a previous outcome" in {
    history =
      Outcome("NAC", "Willem II", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("PSV", "Willem II", 2, 0, new LocalDate(2011, 9, 11)) ::
      Outcome("NAC", "PSV", 0, 1, new LocalDate(2011, 9, 12)) ::
      Nil
    
    assertLatestOutcome(1, 0)
  }
  
  it should "select only the most recent outcome" in {
    history =
      Outcome("NAC", "Willem II", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("NAC", "Willem II", 2, 0, new LocalDate(2010, 9, 10)) ::
      Nil
    
    assertLatestOutcome(1, 0)
  }
  
  it should "return 0-1 when the home team has no matches last year" in {
    history =
      Outcome("Willem II", "Ajax", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("PSV", "Willem II", 2, 0, new LocalDate(2010, 9, 10)) ::
      Nil
    
    assertLatestOutcome(0, 1)
  }
  
  it should "return 1-0 when the out team has no matches last year" in {
    history =
      Outcome("NAC", "Ajax", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("PSV", "NAC", 2, 0, new LocalDate(2010, 9, 10)) ::
      Nil
    
    assertLatestOutcome(1, 0)
  }
  
  it should "return 0-0 if both teams have played, but not against each other (which never happens but still)" in {
    history =
      Outcome("NAC", "Ajax", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("Willem II", "Ajax", 2, 0, new LocalDate(2010, 9, 10)) ::
      Outcome("Ajax", "NAC", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("Ajax", "Willem II", 2, 0, new LocalDate(2010, 9, 10)) ::
      Nil
    
    assertLatestOutcome(0, 0)
  }
  
  it should "return 0-0 when neither team competed last year" in {
    history =
      Outcome("Feyenoord", "Ajax", 1, 0, new LocalDate(2011, 9, 10)) ::
      Outcome("PSV", "Feyenoord", 2, 0, new LocalDate(2010, 9, 10)) ::
      Nil
    
    assertLatestOutcome(0, 0)
  }
}