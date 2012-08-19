package nl.jqno.foobal

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.Mockito.when
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import com.nummulus.boite.Full
import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.io.DateFactory
import nl.jqno.foobal.io.Files
import nl.jqno.foobal.io.Url
import nl.jqno.foobal.predictoutcomes.Predicter
import nl.jqno.foobal.updateoutcomes.OutcomesUpdater
import com.nummulus.boite.Failure
import java.io.IOException

@RunWith(classOf[JUnitRunner])
class MainTest extends FlatSpec with ShouldMatchers with MockitoSugar {
  val Today     = new LocalDate(2012, 7, 20)
  val Url       = "http://www.google.com"
  val Outfile   = "/tmp/foobal.xml"

  val clock     = mock[DateFactory]
  val files     = mock[Files]
  val predicter = mock[Predicter]
  val updater   = mock[OutcomesUpdater]
  val main      = new Main(clock, files, updater, predicter)
  
  when (clock.today) thenReturn Today
  when (files.importFrom(Outfile)) thenReturn Full(List())
  
  
  behavior of "Main"
  
  it should "be able to start the updater" in {
    main.start(Array("update", Url, Outfile)) should be ("OK")
    verify (updater).update(new Url(Url), Outfile)
  }
  
  it should "be able to start the predicter" in {
    predict("NAC", "PSV", 10, 10)
    main.start(Array("predict", Outfile, "NAC", "PSV")) should be (Outcome("NAC", "PSV", 10, 10, Today) toString)
    verify (files).importFrom(Outfile)
    verify (predicter).predict(List(), "NAC", "PSV", Today)
  }
  
  it should "fail when the predicter can't find the file" in {
    val nonExistentFile = "/tmp/does_not_exist"
    when (files.importFrom(nonExistentFile)) thenReturn Failure(new IOException)
    main.start(Array("predict", nonExistentFile, "NAC", "PSV")) should be (Main.FileNotFoundText)
  }
  
  it should "fail when the first parameter is incorrect" in {
    main.start(Array("this", "should", "fail")) should be (Main.HelpText)
  }
  
  it should "fail when not enough parameters are given" in {
    main.start(Array()) should be (Main.HelpText)
  }
  
  it should "fail when too many parameters are given" in {
    main.start(Array("update", "one", "two", "three")) should be (Main.HelpText)
  }
  
  private def predict(homeTeam: String, outTeam: String, homeScore: Int, outScore: Int) {
    when (predicter.predict(List(), homeTeam, outTeam, Today)) thenReturn Outcome(homeTeam, outTeam, homeScore, outScore, Today)
  }
}