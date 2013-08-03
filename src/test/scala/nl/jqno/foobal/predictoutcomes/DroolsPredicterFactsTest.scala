package nl.jqno.foobal.predictoutcomes

import org.drools.FactHandle
import org.drools.KnowledgeBase
import org.drools.runtime.StatefulKnowledgeSession
import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.scalatest.FlatSpec
import org.scalatest.OneInstancePerTest
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

import com.nummulus.boite._

import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.domain.Ranking
import nl.jqno.foobal.domain.ScoreKeeper
import nl.jqno.foobal.test_data.SampleData._

@RunWith(classOf[JUnitRunner])
class DroolsPredicterFactsTest extends FlatSpec with ShouldMatchers with MockitoSugar with OneInstancePerTest {
  var facts = List[Object]()
  val scoreKeeper = mock[ScoreKeeper]
  val engine = createEngine
  
  val thisSeason = new LocalDate(2012, 9, 1)
  val prevSeason = new LocalDate(2011, 9, 1)
  
  
  behavior of "DroolsPredicter's facts"
  
  it should "contain the scoreKeeper" in {
    insertTheFacts(null, Nil)
    (facts contains scoreKeeper) should be (true)
  }
  
  it should "contain all valid outcomes" in {
    insertTheFacts(thisSeason, ValidOutcomes_2)
    ValidOutcomes_2 foreach { f => (facts contains f) should be (true) }
  }
  
  it should "contain the rankings" in {
    insertTheFacts(thisSeason, ValidOutcomes_2)
    leaderboardFacts should have size 4
  }
  
  it should "not contain any rankings from last season" in {
    insertTheFacts(thisSeason, Outcome("Ajax", "FC Doemaarwat", 0, 0, new LocalDate(2012, 5, 10)) :: ValidOutcomes_2)
    leaderboardFacts should have size 4
  }
  
  it should "not contain any rankins from a future season" in {
    insertTheFacts(prevSeason, Outcome("Ajax", "FC Doemaarwat", 0, 0, new LocalDate(2012, 5, 10)) :: ValidOutcomes_2)
    leaderboardFacts should have size 2
  }
  
  private def leaderboardFacts = facts filter (_.isInstanceOf[Ranking])

  private def createEngine = {
    val session = mock[StatefulKnowledgeSession]
    when (session.insert(any())) `then` (new Answer[FactHandle] {
      def answer(invocation: InvocationOnMock) = {
        facts ::= invocation.getArguments()(0)
        null
      }
    })
    val engine = mock[KnowledgeBase]
    when (engine.newStatefulKnowledgeSession) thenReturn session
    engine
  }
  
  private def insertTheFacts(date: LocalDate, initalFacts: List[Outcome]) {
    val p = new DroolsPredicter(Nil, Full(scoreKeeper), Full(engine))
    p.predict(initalFacts, "", "", date)
  }
}