import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scala.concurrent.duration.DurationInt

class GatlingChallenge extends Simulation {

  val videoGames = new VideoGames
  val feeders = new Feeders
  val runTime = new RunTimeParameters

  /*** Setup Environment Variables ***/
  def userCount: Int = runTime.getProperty("USERS", "5").toInt

  def rampDuration: Int = runTime.getProperty("RAMP_DURATION", "10").toInt

  def testDuration: Int = runTime.getProperty("DURATION", "60").toInt

  /*** Print Summary of the test ***/
  before {
    println(s"Running test with ${userCount} users")
    println(s"Ramping users over ${rampDuration} seconds")
    println(s"Total Test Duration ${testDuration} seconds")
  }

  /*** Scenario Design ***/
  // Setting up the HTTP protocols
  val httpConf: HttpProtocolBuilder = http
    .baseURL("http://localhost:8080/app/")
    .header("Accept", "application/json")


  val scn: ScenarioBuilder = scenario("GET ALL VIDEO GAMES")
    .forever() {
      // 1. Get all games
      exec(videoGames.getAllVideoGames)

        // 2. create new game
        .exec(videoGames.postNewGame(feeders.returnCustomFeeder()))

        // 3. get details of that single game
        .exec(videoGames.getAndPrintGameDetails(feeders.idNumber))

        // 4. delete the game
        .exec(videoGames.deleteGame(feeders.idNumber))
    }

  /*** Setup Load Simulation ***/
  setUp(
    scn.inject(
      nothingFor(5 seconds),
      rampUsers(userCount) over (rampDuration)
    )
  ).protocols(httpConf).maxDuration(testDuration)
}
