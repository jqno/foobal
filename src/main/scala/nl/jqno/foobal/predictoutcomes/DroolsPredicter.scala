package nl.jqno.foobal.predictoutcomes

import org.drools.KnowledgeBase
import org.drools.KnowledgeBaseFactory
import org.drools.builder.KnowledgeBuilder
import org.drools.builder.KnowledgeBuilderFactory
import org.drools.builder.ResourceType
import org.drools.io.ResourceFactory
import org.joda.time.LocalDate

import nl.jqno.foobal.domain.Outcome
import nl.jqno.foobal.domain.ScoreKeeper

class DroolsPredicter(input: String) extends Predicter {
  private val engine = createEngine
  
  override def predict(history: List[Outcome], homeTeam: String, outTeam: String, date: LocalDate): Outcome = {
    val session = engine.newStatefulKnowledgeSession
    val result  = new ScoreKeeper(homeTeam, outTeam, date)
    
    session.insert(result)
    history foreach { session.insert(_) }
    session.fireAllRules
    
    result.guess
  }
  
  private def createEngine: KnowledgeBase = {
    val builder = createBuilder
    val base = KnowledgeBaseFactory.newKnowledgeBase
    base.addKnowledgePackages(builder.getKnowledgePackages)
    base
  }
  
  private def createBuilder: KnowledgeBuilder = {
    val builder = KnowledgeBuilderFactory.newKnowledgeBuilder
    val url = getClass.getClassLoader.getResource(input)
    builder.add(ResourceFactory.newUrlResource(url), ResourceType.DRL)
    
    if (builder.hasErrors) {
      throw new RuntimeException(builder.getErrors.toString)
    }
    else {
      builder
    }
  }
}