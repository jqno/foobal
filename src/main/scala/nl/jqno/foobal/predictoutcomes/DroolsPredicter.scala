package nl.jqno.foobal.predictoutcomes

import java.io.File

import org.drools.KnowledgeBase
import org.drools.KnowledgeBaseFactory
import org.drools.builder.KnowledgeBuilderFactory
import org.drools.builder.ResourceType
import org.drools.io.ResourceFactory
import org.joda.time.LocalDate

import nl.jqno.foobal.domain.Outcome

class DroolsPredicter(input: String) extends Predicter {
  val engine = createEngine
  
  override def predict(homeTeam: String, outTeam: String, date: LocalDate): Outcome = {
    val session = engine.newStatefulKnowledgeSession
    session.fireAllRules
    null
  }
  
  private def createEngine: KnowledgeBase = {
    val builder = createBuilder
    val base = KnowledgeBaseFactory.newKnowledgeBase
    base.addKnowledgePackages(builder.getKnowledgePackages)
    base
  }
  
  private def createBuilder = {
    val builder = KnowledgeBuilderFactory.newKnowledgeBuilder
    val url = getClass.getClassLoader.getResource(input)
    builder.add(ResourceFactory.newUrlResource(url), ResourceType.DRL)
    if (builder.hasErrors)
      throw new RuntimeException(builder.getErrors.toString)
    else
      builder
  }
}