package nl.jqno.foobal.io

import java.io.File
import com.nummulus.boite._
import nl.jqno.foobal.domain.Outcome
import scala.xml.XML

class Files(val xml: Xml) {
  def importFrom(fileName: String): Box[Set[Outcome]] = Box.wrap {
    val content = xml.loadFile(fileName)
    
    val outcomes = (content \\ "outcomes" \\ "outcome").flatMap { Outcome(_).toList }
    
    if (outcomes.size == 0) null else outcomes.toSet
  }
}