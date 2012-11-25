package nl.jqno.foobal.domain

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.joda.time.LocalDate
import DateUtil._

@RunWith(classOf[JUnitRunner])
class DateUtilTest extends FlatSpec with ShouldMatchers {
  
  behavior of "determineSeasonStartDateFor"
  
  it should "return Aug 1 of the previous year for the earlier months of the year" in {
    EarlyDates foreach { determineSeasonStartDateFor(_) should be (new LocalDate(2011, 8, 1)) }
  }
  
  it should "return Aug 1 of the given year for the later months of the year" in {
    LateDates foreach { determineSeasonStartDateFor(_) should be (new LocalDate(2012, 8, 1)) }
  }
  
  
  behavior of "determineSeasonEndDateFor"
  
  it should "return Jul 31 the given year for the earlier months of the year" in {
    EarlyDates foreach { determineSeasonEndDateFor(_) should be (new LocalDate(2012, 7, 31)) }
  }
  
  it should "return Jul 31 the next year for the later months of the year" in {
    LateDates foreach { determineSeasonEndDateFor(_) should be (new LocalDate(2013, 7, 31)) }
  }
  
  
  behavior of "determineSeasonEndYearFor"
    
  it should "return the given year in the earlier months of the year" in {
    EarlyDates foreach { determineSeasonEndYearFor(_) should be (2012) }
  }

  it should "return the next year in the later months of the year" in {
    LateDates foreach { determineSeasonEndYearFor(_) should be (2013) }
  }
  
  
  val EarlyDates =
      new LocalDate(2012, 1, 1) ::
      new LocalDate(2012, 2, 29) ::
      new LocalDate(2012, 7, 31) ::
      Nil
      
  val LateDates =
      new LocalDate(2012, 8, 1) ::
      new LocalDate(2012, 11, 25) ::
      new LocalDate(2012, 12, 31) ::
      Nil
}