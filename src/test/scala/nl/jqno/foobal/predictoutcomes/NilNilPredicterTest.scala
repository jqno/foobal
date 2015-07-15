package nl.jqno.foobal.predictoutcomes

import nl.jqno.foobal.domain.Outcome
import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class NilNilPredicterTest extends FlatSpec with Matchers {
  val someDate = LocalDate.now
  val predicter = new NilNilPredicter
  
  
  behavior of "A NilNilPredicter"
  
  it should "always predict 0-0" in {
    predicter.predict(List(), "NAC", "PSV", someDate) should be (Outcome("NAC", "PSV", 0, 0, someDate))
  }
}