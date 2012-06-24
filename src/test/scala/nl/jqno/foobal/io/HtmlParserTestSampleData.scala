package nl.jqno.foobal.io

object HtmlParserTestSampleData {
  val VALID_1 = {
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
  
  val VALID_2 = {
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