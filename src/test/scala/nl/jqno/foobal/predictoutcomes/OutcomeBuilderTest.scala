package nl.jqno.foobal.predictoutcomes

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.OneInstancePerTest
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

import nl.jqno.foobal.domain.Outcome

@RunWith(classOf[JUnitRunner])
class OutcomeBuilderTest extends FlatSpec with ShouldMatchers with OneInstancePerTest {
  val home = "Willem II"
  val out  = "NAC"
  val date = new LocalDate(2012, 8, 10)

  val builder = new OutcomeBuilder(home, out, date)
  
  behavior of "An OutcomeBuilder"
  
  it should "build an Outcome" in {
    builder.setOutcome(0, 2)
    builder.build should be (Outcome(home, out, 0, 2, date))
  }
  
  it should "build another Outcome" in {
    builder.setOutcome(2, 0)
    builder.build should be (Outcome(home, out, 2, 0, date))
  }
  
  it should "fail if built without a score" in {
    evaluating { builder.build } should produce [IllegalStateException]
  }
  
  it should "throw an exception when a score is assigned twice" in {
    builder.setOutcome(2, 0)
    evaluating { builder.setOutcome(0, 2) } should produce [IllegalStateException]
  }
}
