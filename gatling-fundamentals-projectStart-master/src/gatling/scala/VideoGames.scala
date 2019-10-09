import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class VideoGames extends Simulation {

  def getAllVideoGames: ChainBuilder = {
    exec(
      http("GET ALL VIDEO GAMES")
        .get("videogames")
        .check(status.is(200))
    )
  }

  def getAndPrintGameDetails(gameId : Int): ChainBuilder = {
    exec(
      http("GET VIDEO GAME BY ID")
        .get("videogames/" + gameId.toString)
        .check(status.is(200))
        .check(bodyString.saveAs("responseBody"))
    )
      .exec{ session => println(session("responseBody").as[String]); session}
  }

  def postNewGame (customFeed: Iterator[Map[String, Any]]): ChainBuilder ={
    feed(customFeed)
      .exec(
        http("CREATE NEW VIDEO GAME")
          .post("videogames/")
          .body(ElFileBody("JsonNewVideoGame.json")).asJSON
          .check(status.is(200))
      )
      .exec { session => println("Video Game created"); session}
  }

  def deleteGame (gameId: Int): ChainBuilder ={
    exec(
      http("DELETE VIDEO GAME BY ID")
        .delete("videogames/" + gameId.toString)
        .check(status.is(200))
    )
      .exec { session => println("Video Game with ID #" + gameId + " was deleted"); session}
  }
}


