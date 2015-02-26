package nl.jqno.foobal.domain

import scala.collection.mutable

import org.joda.time.LocalDate

class ScoreKeeper(val homeTeam: String, val outTeam: String, val date: LocalDate) {
  
  private val scores = new mutable.ArrayBuffer[(Int, Int)]
  
  def add(description: String, home: Int, out: Int): Unit = {
    println(s"Score ($description): $home-$out")
    scores += ((home, out))
  }
  
  def guess: Outcome = {
    val n = scores.size
    if (n == 0) {
      throw new IllegalStateException("No scores found")
    }
    
    val homeScore = median(scores.map(_._1).sorted)
    val outScore  = median(scores.map(_._2).sorted)
    Outcome(homeTeam, outTeam, homeScore, outScore, date)
  }
  
  private def median(xs: IndexedSeq[Int]): Int =
    if (xs.size % 2 == 0)
      evenMedian(xs)
    else
      oddMedian(xs)
  
  private def evenMedian(xs: IndexedSeq[Int]): Int = {
    val mid = xs.size / 2
    val a: Double = xs(mid - 1)
    val b = xs(mid)
    scala.math.ceil((a + b) / 2).toInt
  }
  
  private def oddMedian(xs: IndexedSeq[Int]): Int = xs((xs.size - 1) / 2)
}