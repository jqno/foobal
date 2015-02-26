package nl.jqno.foobal.test_data

import scala.xml.Utility.trim
import org.joda.time.LocalDate
import nl.jqno.foobal.domain.Outcome

object SampleData {
  val MiniSeason =
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
  
  val ValidOutcomes_1 =
    Outcome("PSV", "Feyenoord", 10, 0, new LocalDate(2010, 10, 9)) ::
    Outcome("Ajax", "NAC", 1, 2, new LocalDate(2010, 11, 11)) ::
    Nil

  val ValidXml_1 = trim(
    <outcomes>
      <outcome><homeTeam>PSV</homeTeam><outTeam>Feyenoord</outTeam><homeScore>10</homeScore><outScore>0</outScore><date>2010-10-09</date></outcome>
      <outcome><homeTeam>Ajax</homeTeam><outTeam>NAC</outTeam><homeScore>1</homeScore><outScore>2</outScore><date>2010-11-11</date></outcome>
    </outcomes>
  )
  
  val ValidHtml_1 = """
    |<html><body><center>
    |<table border='0' cellpadding='5' cellspacing='5' align='center'><tr>
    |  <td>2010-2011</td>
    |  <td>2010-10-09</td>
    |  <td nowrap='nowrap'><img src='images/psv.gif' alt='PSV'/> PSV</td>
    |  <td nowrap='nowrap'><img src='images/feyenoord.gif' alt='Feyenoord'/> Feyenoord</td>
    |  <td>10</td>
    |  <td>0</td>
    |</tr><tr>
    |  <td>2010-2011</td>
    |  <td>2010-11-11</td>
    |  <td nowrap='nowrap'><img src='images/ajax.gif' alt='Ajax'/> Ajax</td>
    |  <td nowrap='nowrap'><img src='images/nac.gif' alt='NAC'/> NAC</td>
    |  <td>1</td>
    |  <td>2</td>
    |</tr></table>
    |</center></body></html>
    """.stripMargin
  
  // contains an &nbsp;, a missing </tr> tag and a missing </td> tag
  val ValidButMalformedHtml_1 = """
    |<html><body><center>
    |<table border='0' cellpadding='5' cellspacing='5' align='center'><tr>
    |  <td>&nbsp;</td>
    |  <td>2010-10-09</td>
    |  <td nowrap='nowrap'><img src='images/psv.gif' alt='PSV'/> PSV</td>
    |  <td nowrap='nowrap'><img src='images/feyenoord.gif' alt='Feyenoord'/> Feyenoord</td>
    |  <td>10</td>
    |  <td>0</td>
    |<tr>
    |  <td>2010-2011</td>
    |  <td>2010-11-11</td>
    |  <td nowrap='nowrap'><img src='images/ajax.gif' alt='Ajax'/> Ajax</td>
    |  <td nowrap='nowrap'><img src='images/nac.gif' alt='NAC'/> NAC</td>
    |  <td>1
    |  <td>2</td>
    |</tr></table>
    |</center></body></html>
    """.stripMargin
  
  val ValidOutcomes_2 =
    Outcome("NAC", "PSV", 2, 0, new LocalDate(2012, 8, 17)) ::
    Outcome("FC Appelmoesboerdonk", "Willem II", 2, 1, new LocalDate(2012, 8, 18)) ::
    Outcome("PSV", "NAC", 1, 1, new LocalDate(2012, 8, 24)) ::
    Outcome("Willem II", "FC Appelmoesboerdonk", 2, 1, new LocalDate(2012, 8, 25)) ::
    Nil
  
  val ValidXml_2 = trim(
    <outcomes>
      <outcome><homeTeam>NAC</homeTeam><outTeam>PSV</outTeam><homeScore>2</homeScore><outScore>0</outScore><date>2012-08-17</date></outcome>
      <outcome><homeTeam>FC Appelmoesboerdonk</homeTeam><outTeam>Willem II</outTeam><homeScore>2</homeScore><outScore>1</outScore><date>2012-08-18</date></outcome>
      <outcome><homeTeam>PSV</homeTeam><outTeam>NAC</outTeam><homeScore>1</homeScore><outScore>1</outScore><date>2012-08-24</date></outcome>
      <outcome><homeTeam>Willem II</homeTeam><outTeam>FC Appelmoesboerdonk</outTeam><homeScore>2</homeScore><outScore>1</outScore><date>2012-08-25</date></outcome>
    </outcomes>
  )
  
  val ValidHtml_2 = """
    |<html><body><center>
    |<table border='0' cellpadding='5' cellspacing='5' align='center'><tr>
    |  <td>2012-2013</td>
    |  <td>2012-08-17</td>
    |  <td nowrap='nowrap'><img src='images/nac.gif' alt='NAC'/> NAC</td>
    |  <td nowrap='nowrap'><img src='images/psv.gif' alt='PSV'/> PSV</td>
    |  <td>2</td>
    |  <td>0</td>
    |</tr><tr>
    |  <td>2012-2013</td>
    |  <td>2012-08-18</td>
    |  <td nowrap='nowrap'><img src='images/fc%20appelmoesboerdonk.gif' alt='FC Appelmoesboerdonk'/> FC Appelmoesboerdonk</td>
    |  <td nowrap='nowrap'><img src='images/willem%20ii.gif' alt='Willem II'/> Willem II</td>
    |  <td>2</td>
    |  <td>1</td>
    |</tr></table>
    |<table border='0' cellpadding='5' cellspacing='5' align='center'><tr>
    |  <td>2012-2013</td>
    |  <td>2012-08-24</td>
    |  <td nowrap='nowrap'><img src='images/psv.gif' alt='PSV'/> PSV</td>
    |  <td nowrap='nowrap'><img src='images/nac.gif' alt='NAC'/> NAC</td>
    |  <td>1</td>
    |  <td>1</td>
    |</tr><tr>
    |  <td>2012-2013</td>
    |  <td>2012-08-25</td>
    |  <td nowrap='nowrap'><img src='images/willem%20ii.gif' alt='Willem II'/> Willem II</td>
    |  <td nowrap='nowrap'><img src='images/fc%20appelmoesboerdonk.gif' alt='FC Appelmoesboerdonk'/> FC Appelmoesboerdonk</td>
    |  <td>2</td>
    |  <td>1</td>
    |</tr></table>
    |</center></body></html>
    """.stripMargin
 
  val OutcomesWithNonContentTables =
    Outcome("NAC", "PSV", 2, 0, new LocalDate(2012, 5, 17)) ::
    Outcome("FC Appelmoesboerdonk", "Willem II", 2, 1, new LocalDate(2012, 5, 18)) ::
    Nil
  
  val HtmlWithNonContentTables = """
    |<html><body><center>
    |<table border='0' cellpadding='5' cellspacing='5' align='center'><tr>
    |  <th>2011-2012</th>
    |  <th>2012-05-24</th>
    |  <th nowrap='nowrap'><img src='images/psv.gif' alt='PSV'/> PSV</th>
    |  <th nowrap='nowrap'><img src='images/nac.gif' alt='NAC'/> NAC</th>
    |  <th>1</th>
    |  <th>1</th>
    |</tr><tr>
    |  <th>2011-2012</th>
    |  <th>2012-05-25</th>
    |  <th nowrap='nowrap'><img src='images/willem%20ii.gif' alt='Willem II'/> Willem II</th>
    |  <th nowrap='nowrap'><img src='images/fc%20appelmoesboerdonk.gif' alt='FC Appelmoesboerdonk'/> FC Appelmoesboerdonk</th>
    |  <th>2</th>
    |  <th>1</th>
    |</tr></table>
    |<table><tbody><tr><td><script></script></td></tr></table>
    |<table><tbody><tr><td><script></script></td></tr></table>
    |<table border='0' cellpadding='5' cellspacing='5' align='center'><tr>
    |  <td>2011-2012</td>
    |  <td>2012-05-17</td>
    |  <td nowrap='nowrap'><img src='images/nac.gif' alt='NAC'/> NAC</td>
    |  <td nowrap='nowrap'><img src='images/psv.gif' alt='PSV'/> PSV</td>
    |  <td>2</td>
    |  <td>0</td>
    |</tr><tr>
    |  <td>2011-2012</td>
    |  <td>2012-05-18</td>
    |  <td nowrap='nowrap'><img src='images/fc%20appelmoesboerdonk.gif' alt='FC Appelmoesboerdonk'/> FC Appelmoesboerdonk</td>
    |  <td nowrap='nowrap'><img src='images/willem%20ii.gif' alt='Willem II'/> Willem II</td>
    |  <td>2</td>
    |  <td>1</td>
    |</tr></table>
    |</center></body></html>
    """.stripMargin
 
  val HtmlWithDtd = """|<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
    |<body><table></table></body>
    """.stripMargin
}