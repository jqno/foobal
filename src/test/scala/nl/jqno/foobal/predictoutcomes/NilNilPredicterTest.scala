package nl.jqno.foobal.predictoutcomes

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.joda.time.LocalDate
import nl.jqno.foobal.domain.Outcome

@RunWith(classOf[JUnitRunner])
class NilNilPredicterTest extends FlatSpec with ShouldMatchers {
  val Date = LocalDate.now
  val predicter = new NilNilPredicter
  
  
  behavior of "A NilNilPredicter"
  
  it should "always predict 0-0" in {
    predicter.predict(List(), "NAC", "PSV", Date) should be (Outcome("NAC", "PSV", 0, 0, Date))
  }
}