package nl.jqno.foobal.io

import java.io.IOException

import scala.xml.Utility.trim

import com.nummulus.boite._

import nl.jqno.foobal.domain.Outcome

class Files(xml: Xml = new Xml) {
  def importFrom(fileName: String): Box[List[Outcome]] = Box wrap {
    val content = xml.loadFile(fileName)
    val outcomes = (content \\ "outcomes" \\ "outcome") flatMap { Outcome(_).toList }
    
    if (outcomes.size == 0) null else outcomes.toList
  }
  
  def exportTo(fileName: String, outcomes: List[Outcome]) {
    val data = <outcomes>{outcomes map { _.toXml }}</outcomes>
    xml.saveFile(fileName, trim(data))
  }
}