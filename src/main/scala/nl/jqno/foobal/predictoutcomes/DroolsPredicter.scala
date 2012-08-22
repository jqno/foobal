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
import com.nummulus.boite.Empty
import com.nummulus.boite.Box
import java.io.File

class DroolsPredicter(source: String, scoreKeeper: Box[ScoreKeeper] = Empty) extends Predicter {
  private val engine = createEngine
  
  override def predict(history: List[Outcome], homeTeam: String, outTeam: String, date: LocalDate): Outcome = {
    val session = engine.newStatefulKnowledgeSession
    val result  = scoreKeeper getOrElse new ScoreKeeper(homeTeam, outTeam, date)
    
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
    val file = new File(getClass.getClassLoader.getResource(source).toURI)
    val files = if (file.isDirectory) file.listFiles.toList else List(file)
    
    files foreach { f => builder.add(ResourceFactory.newFileResource(f), ResourceType.DRL) }
    
    if (builder.hasErrors) {
      throw new RuntimeException(builder.getErrors.toString)
    }
    else {
      builder
    }
  }
}