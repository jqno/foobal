package nl.jqno.foobal

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.Mockito.when
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatest.FlatSpec

import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.io.DateFactory
import nl.jqno.foobal.io.Url
import nl.jqno.foobal.predictoutcomes.Predicter
import nl.jqno.foobal.updateoutcomes.OutcomesUpdater

@RunWith(classOf[JUnitRunner])
class MainTest extends FlatSpec with ShouldMatchers with MockitoSugar {
  val predicter = mock[Predicter]
  val updater = mock[OutcomesUpdater]
  val clock = mock[DateFactory]
  val today = new LocalDate(2012, 7, 20)
  when(clock.today) thenReturn today
  
  val main = new Main(clock, updater, predicter)
  
  val url = "http://www.google.com"
  val outfile = "/tmp/foobal.xml"
  
  behavior of "Main"
  
  it should "be able to start the updater" in {
    main.start(Array("update", url, outfile)) should be ("OK")
    verify(updater).update(new Url(url), outfile)
  }
  
  it should "be able to start the predicter" in {
    predict("NAC", "PSV", 10, 10)
    main.start(Array("predict", "NAC", "PSV")) should be (Outcome("NAC", "PSV", 10, 10, today) toString)
    verify(predicter).predict("NAC", "PSV", today)
  }
  
  it should "fail when the first parameter is incorrect" in {
    evaluating { main.start(Array("this", "should", "fail")) } should produce [IllegalArgumentException]
  }
  
  it should "fail when not enough parameters are given" in {
    evaluating { main.start(Array()) } should produce [IllegalArgumentException]
  }
  
  it should "fail when too many parameters are given" in {
    evaluating { main.start(Array("update", "one", "two", "three")) } should produce [IllegalArgumentException]
  }
  
  def predict(homeTeam: String, outTeam: String, homeScore: Int, outScore: Int) =
    when(predicter.predict(homeTeam, outTeam, today)) thenReturn Outcome(homeTeam, outTeam, homeScore, outScore, today)
}