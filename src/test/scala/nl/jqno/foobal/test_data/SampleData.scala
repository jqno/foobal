package nl.jqno.foobal.test_data

import nl.jqno.foobal.domain.Outcome
import org.joda.time.LocalDate

import scala.xml.Utility.trim

object SampleData {
  val miniSeason =
    Outcome("NAC",       "Ajax",      4, 0, new LocalDate(2012, 9, 10)) ::
    Outcome("Ajax",      "NAC",       0, 0, new LocalDate(2012, 9, 11)) ::
    Outcome("Willem II", "PSV",       0, 1, new LocalDate(2012, 9, 12)) ::
    Outcome("PSV",       "Willem II", 1, 1, new LocalDate(2012, 9, 13)) ::
    Outcome("NAC",       "Willem II", 8, 2, new LocalDate(2012, 9, 14)) ::
    Outcome("Willem II", "NAC",       1, 4, new LocalDate(2012, 9, 15)) ::
    Outcome("Ajax",      "Willem II", 4, 0, new LocalDate(2012, 9, 16)) ::
    Outcome("Willem II", "Ajax",      2, 2, new LocalDate(2012, 9, 17)) ::
    Outcome("NAC",       "PSV",       2, 1, new LocalDate(2012, 9, 18)) ::
    Outcome("PSV",       "NAC",       1, 6, new LocalDate(2012, 9, 19)) ::
    Nil 
  
  val validOutcomes1 =
    Outcome("PSV", "Feyenoord", 10, 0, new LocalDate(2010, 10, 9)) ::
    Outcome("Ajax", "NAC", 1, 2, new LocalDate(2010, 11, 11)) ::
    Nil

  val validXml1 = trim(
    <outcomes>
      <outcome><homeTeam>PSV</homeTeam><outTeam>Feyenoord</outTeam><homeScore>10</homeScore><outScore>0</outScore><date>2010-10-09</date></outcome>
      <outcome><homeTeam>Ajax</homeTeam><outTeam>NAC</outTeam><homeScore>1</homeScore><outScore>2</outScore><date>2010-11-11</date></outcome>
    </outcomes>
  )
  
  val validHtml1 = """
    |<html><body>
    |<div id="content-wrap"><div id="content"><div id="main"><div id="contentBlock"><div id="events"><div id="past">
    |<h3></h3>
    |<b> 9 oktober 2010</b>
    |<div class="line even">
    |  <div class="center score"><span><a>10 - 0</a></span></div>
    |  <div class="float-left club"><a>PSV <img></img></a></div>
    |  <div class="float-right club"><a><img></img> Feyenoord</a></div>
    |</div>
    |<b>11 november 2010</b>
    |<div class="line odd">
    |  <div class="center score"><span><a>1 - 2</a></span></div>
    |  <div class="float-left club"><a>Ajax <img></img></a></div>
    |  <div class="float-right club"><a><img></img> NAC</a></div>
    |</div>
    |</div></div></div></div></div></div>
    |</body></html>
    """.stripMargin
  
  // contains an &nbsp;, a missing </span> and a missing </a> tag
  val validButMalformedHtml1 = """
    |<html><body>
    |<div id="content-wrap"><div id="content"><div id="main"><div id="contentBlock"><div id="events"><div id="past">
    |<h3></h3>
    |<b> 9 oktober 2010</b>
    |<div class="line even">
    |  <div class="center score"><span><a>10 - 0</a></div>
    |  <div class="float-left club"><a>PSV <img></img></a></div>
    |  <div class="float-right club"><img></img> Feyenoord</a></div>
    |</div>
    |<b>11 november 2010</b>
    |<div class="line odd">
    |  <div class="center score"><span><a>1 - 2</a></span></div>
    |  <div class="float-left club"><a>Ajax <img></img></a></div>&nbsp;
    |  <div class="float-right club"><a><img></img> NAC</a></div>
    |</div>
    |</div></div></div></div></div></div>
    |</body></html>
    """.stripMargin

  val validHtmlWithInvalidIgnoreableEntry1 = """
    |<html><body>
    |<div id="content-wrap"><div id="content"><div id="main"><div id="contentBlock"><div id="events"><div id="past">
    |<h3></h3>
    |<b> 9 oktober 2010</b>
    |<div class="line even">
    |  <div class="center score"><span><a>10 - 0</a></span></div>
    |  <div class="float-left club"><a>PSV <img></img></a></div>
    |  <div class="float-right club"><a><img></img> Feyenoord</a></div>
    |</div>
    |<b>11 november 2010</b>
    |<div class="line odd">
    |  <div class="center score"><span><a>1 - 2</a></span></div>
    |  <div class="float-left club"><a>Ajax <img></img></a></div>
    |  <div class="float-right club"><a><img></img> NAC</a></div>
    |</div>
    |<b>00 november 0000</b>
    |<div class="line even">
    |  <div class="center score"><span><a>1 - 2</a></span></div>
    |  <div class="float-left club"><a>PSV <img></img></a></div>
    |  <div class="float-right club"><a><img></img> NAC</a></div>
    |</div>
    |</div></div></div></div></div></div>
    |</body></html>
    """.stripMargin
 
  val validOutcomes2 =
    Outcome("NAC", "PSV", 2, 0, new LocalDate(2012, 8, 17)) ::
    Outcome("FC Appelmoesboerdonk", "Willem II", 2, 1, new LocalDate(2012, 8, 17)) ::
    Outcome("PSV", "NAC", 1, 1, new LocalDate(2012, 8, 24)) ::
    Outcome("Willem II", "FC Appelmoesboerdonk", 2, 1, new LocalDate(2012, 8, 24)) ::
    Nil
  
  val validXml2 = trim(
    <outcomes>
      <outcome><homeTeam>NAC</homeTeam><outTeam>PSV</outTeam><homeScore>2</homeScore><outScore>0</outScore><date>2012-08-17</date></outcome>
      <outcome><homeTeam>FC Appelmoesboerdonk</homeTeam><outTeam>Willem II</outTeam><homeScore>2</homeScore><outScore>1</outScore><date>2012-08-17</date></outcome>
      <outcome><homeTeam>PSV</homeTeam><outTeam>NAC</outTeam><homeScore>1</homeScore><outScore>1</outScore><date>2012-08-24</date></outcome>
      <outcome><homeTeam>Willem II</homeTeam><outTeam>FC Appelmoesboerdonk</outTeam><homeScore>2</homeScore><outScore>1</outScore><date>2012-08-24</date></outcome>
    </outcomes>
  )
  
  val validHtml2 = """
    |<html><body>
    |<div id="content-wrap"><div id="content"><div id="main"><div id="contentBlock"><div id="events"><div id="past">
    |<h3></h3>
    |<b>17 augustus 2012</b>
    |<div class="line even">
    |  <div class="center score"><span><a>2 - 0</a></span></div>
    |  <div class="float-left club"><a>NAC <img></img></a></div>
    |  <div class="float-right club"><a><img></img> PSV</a></div>
    |</div>
    |<div class="line odd">
    |  <div class="center score"><span><a>2 - 1</a></span></div>
    |  <div class="float-left club"><a>FC Appelmoesboerdonk <img></img></a></div>
    |  <div class="float-right club"><a><img></img> Willem II</a></div>
    |</div>
    |<b>24 augustus 2012</b>
    |<div class="line even">
    |  <div class="center score"><span><a>1 - 1</a></span></div>
    |  <div class="float-left club"><a>PSV <img></img></a></div>
    |  <div class="float-right club"><a><img></img> NAC</a></div>
    |</div>
    |<div class="line odd">
    |  <div class="center score"><span><a>2 - 1</a></span></div>
    |  <div class="float-left club"><a>Willem II <img></img></a></div>
    |  <div class="float-right club"><a><img></img> FC Appelmoesboerdonk</a></div>
    |</div>
    |</div></div></div></div></div></div>
    |</body></html>
    """.stripMargin
 
  val htmlWithDtd = """|<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
    |<body><div></div></body>
    """.stripMargin
}