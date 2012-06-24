package nl.jqno.foobal.domain

import org.joda.time.LocalDate

case class Outcome(
    homeTeam: String,
    outTeam: String,
    homeScore: Int,
    outScore: Int,
    date: LocalDate
)
