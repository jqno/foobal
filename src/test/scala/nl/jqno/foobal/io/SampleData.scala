package nl.jqno.foobal.io

import scala.xml.Utility.trim

import org.joda.time.LocalDate

import nl.jqno.foobal.domain.Outcome

object SampleData {
  val VALID_1_OUTCOMES = List(
    Outcome("PSV", "Feyenoord", 10, 0, new LocalDate(2010, 10, 24)),
    Outcome("Ajax", "NAC", 1, 2, new LocalDate(2010, 11, 11))
  )

  val VALID_1_XML = trim(
    <outcomes>
      <outcome><homeTeam>PSV</homeTeam><outTeam>Feyenoord</outTeam><homeScore>10</homeScore><outScore>0</outScore><date>2010-10-24</date></outcome>
      <outcome><homeTeam>Ajax</homeTeam><outTeam>NAC</outTeam><homeScore>1</homeScore><outScore>2</outScore><date>2010-11-11</date></outcome>
    </outcomes>
  )
  
  val VALID_1_HTML = {
    val result = """
        |<html><body><div id=""><div id="">
        |<table cellspacing="1" class="schema">
        |<tr class="odd">
        |  <td style="">zo 24 okt, 20:00</td>
        |  <td><a href="">PSV</a></td>
        |  <td><a href="">Feyenoord</a></td>
        |  <td style=""><a href="">10-0</a></td>
        |  <td style=""><a href="" title=""><img src="" width="" height="" alt="" style="" /></a><a href=""><img src="" width="" height="" alt="" title="" style="" /></a></td>
        |</tr>
        |<tr class="even">
        |  <td style="">vr 11 nov, 18:45</td>
        |  <td><a href="">Ajax</a></td>
        |  <td><a href="">NAC</a></td>
        |  <td style=""><a href="">1-2</a></td>
        |  <td style=""><a href="" title=""><img src="" width="" height="" alt="" style="" /></a><a href=""><img src="" width="" height="" alt="" title="" style="" /></a></td>
        |</tr>
        |</table>
        |</div></div></body></html>
      """
      result.stripMargin
  }
  
  val VALID_2_OUTCOMES = List(
    Outcome("NAC", "PSV", 2, 0, new LocalDate(2012, 5, 17)),
    Outcome("FC Appelmoesboerdonk", "Willem II", 2, 1, new LocalDate(2012, 5, 18)),
    Outcome("PSV", "NAC", 1, 1, new LocalDate(2012, 5, 24)),
    Outcome("Willem II", "FC Appelmoesboerdonk", 2, 1, new LocalDate(2012, 5, 25))
  )
  
  val VALID_2_XML = trim(
    <outcomes>
      <outcome><homeTeam>NAC</homeTeam><outTeam>PSV</outTeam><homeScore>2</homeScore><outScore>0</outScore><date>2012-05-17</date></outcome>
      <outcome><homeTeam>FC Appelmoesboerdonk</homeTeam><outTeam>Willem II</outTeam><homeScore>2</homeScore><outScore>1</outScore><date>2012-05-18</date></outcome>
      <outcome><homeTeam>PSV</homeTeam><outTeam>NAC</outTeam><homeScore>1</homeScore><outScore>1</outScore><date>2012-05-24</date></outcome>
      <outcome><homeTeam>Willem II</homeTeam><outTeam>FC Appelmoesboerdonk</outTeam><homeScore>2</homeScore><outScore>1</outScore><date>2012-05-25</date></outcome>
    </outcomes>
  )
  
  val VALID_2_HTML = {
    val result = """
        |<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="nl" lang="nl"
        |  xmlns:og="http://ogp.me/ns#" xmlns:fb="https://www.facebook.com/2008/fbml">
        |<body><div id="body"><div id="middle_container">
        |<table cellspacing="1" class="schema">
        |<tr class="odd">
        |  <td style="font-size: 11px; width:88px;">do 17 mei, 20:00</td>
        |  <td><a href="/blablabla/nac">NAC</a></td>
        |  <td><a href="/blablabla/psv">PSV</a></td>
        |  <td style="text-align:center;"><a href="/blablabla/nac-psv">2-0</a></td>
        |</tr>
        |<tr class="even">
        |  <td style="font-size: 11px; width:88px;">vr 18 mei, 18:45</td>
        |  <td><a href="/tralala/fc-appelmoesboerdonk">FC Appelmoesboerdonk</a></td>
        |  <td><a href="/tralala/willem-ii">Willem II</a></td>
        |  <td style="text-align:center;"><a href="/tralala/fc-appelmoesboerdonk-willem-ii">2-1</a></td>
        |</tr>
        |</table>
        |<table cellspacing="1" class="schema">
        |<tr class="odd">
        |  <td style="font-size: 11px; width:88px;">do 24 mei, 20:00</td>
        |  <td><a href="/blablabla/psv">PSV</a></td>
        |  <td><a href="/blablabla/nac">NAC</a></td>
        |  <td style="text-align:center;"><a href="/blablabla/nac-psv">1-1</a></td>
        |</tr>
        |<tr class="even">
        |  <td style="font-size: 11px; width:88px;">vr 25 mei, 18:45</td>
        |  <td><a href="/tralala/willem-ii">Willem II</a></td>
        |  <td><a href="/tralala/fc-appelmoesboerdonk">FC Appelmoesboerdonk</a></td>
        |  <td style="text-align:center;"><a href="/tralala/fc-appelmoesboerdonk-willem-ii">2-1</a></td>
        |</tr>
        |</table>
        |</div></div></body></html>
    """
    result.stripMargin
  }
  
  val DTD = {
    val result = """|<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
      |<body><table></table></body>
    """
    result.stripMargin
  }
}