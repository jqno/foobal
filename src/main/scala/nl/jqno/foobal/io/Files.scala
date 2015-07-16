package nl.jqno.foobal.io

import nl.jqno.foobal.domain.Outcome

import scala.util.Try
import scala.xml.Utility.trim

class Files(xml: Xml = new Xml) {
  def importFrom(fileName: String): Try[List[Outcome]] = Try {
    val content = xml.loadFile(fileName)
    val outcomes = (content \\ "outcomes" \\ "outcome") flatMap (Outcome(_).toOption.toList)
    
    if (outcomes.isEmpty)
      throw new IllegalStateException(s"No history found in $fileName")
    else
      outcomes.toList
  }
  
  def exportTo(fileName: String, outcomes: List[Outcome]) {
    val data = <outcomes>{outcomes map (_.toXml)}</outcomes>
    xml.saveFile(fileName, trim(data))
  }
}