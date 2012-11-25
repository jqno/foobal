package nl.jqno.foobal.domain

import org.joda.time.LocalDate

object DateUtil {
  val SeasonEndMonth = 7
  
  def determineSeasonStartDateFor(date: LocalDate): LocalDate = {
    val thisYear = date.year.get
    val seasonStartYear = if (date.monthOfYear.get > SeasonEndMonth) thisYear else thisYear - 1
    new LocalDate(seasonStartYear, 8, 1)
  }
  
  def determineSeasonEndDateFor(date: LocalDate): LocalDate = {
    val thisYear = date.year.get
    val seasonStartYear = if (date.monthOfYear.get > SeasonEndMonth) thisYear + 1 else thisYear
    new LocalDate(seasonStartYear, 7, 31)
  }
  
  def determineSeasonEndYearFor(date: LocalDate): Int = {
    determineSeasonEndDateFor(date).year.get
  }
}