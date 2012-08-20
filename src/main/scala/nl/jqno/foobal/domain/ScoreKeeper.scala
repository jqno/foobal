package nl.jqno.foobal.domain

import nl.jqno.foobal.io.Random
import scala.collection.mutable
import org.joda.time.LocalDate

class ScoreKeeper(
    val homeTeam: String,
    val outTeam: String,
    val date: LocalDate,
    random: Random = new Random) {
  
  private val scores = new mutable.ArrayBuffer[(Int, Int)]
  
  def add(home: Int, out: Int) {
    scores += ((home, out))
  }
  
  def guess: Outcome = {
    val n = scores.size
    if (n == 0) {
      throw new IllegalStateException("No scores found")
    }
    
    val (homeScore, outScore) = scores(random.nextInt(n))
    Outcome(homeTeam, outTeam, homeScore, outScore, date)
  }
}