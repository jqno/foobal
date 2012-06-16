package nl.jqno.foobal.parser

import org.joda.time.LocalDate

case class Outcome(
    homeTeam: String,
    outTeam: String,
    homeScore: Int,
    outScore: Int,
    date: LocalDate
)
