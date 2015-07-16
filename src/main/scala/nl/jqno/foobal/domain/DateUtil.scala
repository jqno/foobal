package nl.jqno.foobal.domain

import org.joda.time.LocalDate

object DateUtil {
  val seasonEndMonth = 7
  private val lastDayOfSeasonEndMonth = 31
  
  def determineSeasonStartDateFor(date: LocalDate): LocalDate = {
    val thisYear = date.year.get
    val seasonStartYear = if (date.monthOfYear.get > seasonEndMonth) thisYear else thisYear - 1
    new LocalDate(seasonStartYear, seasonEndMonth + 1, 1)
  }
  
  def determineSeasonEndDateFor(date: LocalDate): LocalDate = {
    val thisYear = date.year.get
    val seasonStartYear = if (date.monthOfYear.get > seasonEndMonth) thisYear + 1 else thisYear
    new LocalDate(seasonStartYear, seasonEndMonth, lastDayOfSeasonEndMonth)
  }
  
  def determineSeasonEndYearFor(date: LocalDate): Int =
    determineSeasonEndDateFor(date).year.get
}