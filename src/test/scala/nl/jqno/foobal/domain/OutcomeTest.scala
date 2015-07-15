package nl.jqno.foobal.domain

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

import scala.util.{Failure, Success}
import scala.xml.Utility.trim

@RunWith(classOf[JUnitRunner])
class OutcomeTest extends FlatSpec with Matchers {
  
  val Example = Outcome("Ajax", "NAC", 1, 2, new LocalDate(2010, 11, 11))
  val Xml = 
    trim(<outcome>
      <homeTeam>Ajax</homeTeam>
      <outTeam>NAC</outTeam>
      <homeScore>1</homeScore>
      <outScore>2</outScore>
      <date>2010-11-11</date>
    </outcome>)
  
  
  behavior of "An Outcome"
  
  it should "convert to xml" in {
    trim(Example.toXml) should be (Xml)
  }
  
  it should "convert from xml" in {
    Outcome(Xml) should be (Success(Example))
  }
  
  it should "fail to convert from invalid xml" in {
    val Failure(f) = Outcome(<outcome>0</outcome>)
    f.getClass should be (classOf[NumberFormatException])
  }
}
