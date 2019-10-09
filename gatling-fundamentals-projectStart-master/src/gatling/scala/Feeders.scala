import java.time.LocalDate
import java.time.format.DateTimeFormatter

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

class Feeders extends Simulation {

  val idNumber: Int = Random.nextInt(99999)
  private val rmd = new Random()
  private val now: LocalDate = LocalDate.now()
  private val pattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd")

  /** * Helper Methods ***/
  // for the custom feeder, or for the defaults of the runtime parameters... and anything else
 private def randomString (length: Int) = {
    rmd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  private def getRandomDate(startDate: LocalDate, random: Random):String = {
    startDate.minusDays(random.nextInt(30)).format(pattern)
  }

  def returnCustomFeeder (): Iterator[Map[String, Any]]={
    // to generate the data for the Create New Game JSON
    val customFeeder: Iterator[Map[String, Any]] = Iterator.continually(Map(
      "gameId" -> idNumber,
      "name" -> ("Game-" + randomString(5)),
      "releaseDate"-> getRandomDate(now, rmd),
      "reviewScore"-> rmd.nextInt(100),
      "category"-> ("Category-" + randomString(6)),
      "rating"-> ("Rating-" + randomString(4))
    ))
    return customFeeder
  }
}
