
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://computer-database.gatling.io")
		.inferHtmlResources()
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9,es-US;q=0.8,es;q=0.7",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9,es-US;q=0.8,es;q=0.7",
		"Origin" -> "http://computer-database.gatling.io",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("RecordedSimulation")
		.exec(http("LOAD HOME PAGE")
			.get("/computers")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/assets/stylesheets/bootstrap.min.css"),
            http("request_2")
			.get("/assets/stylesheets/main.css")))
		.pause(1)
		.exec(http("LOAD NEW COMPUTER PAGE")
			.get("/computers/new")
			.headers(headers_0)
			.resources(http("request_4")
			.get("/assets/stylesheets/bootstrap.min.css"),
            http("request_5")
			.get("/assets/stylesheets/main.css")))
		.pause(1)
		.exec(http("CREATE NEW COMPUTER")
			.post("/computers")
			.headers(headers_6)
			.formParam("name", "EduTest2")
			.formParam("introduced", "2019-10-05")
			.formParam("discontinued", "2020-10-06")
			.formParam("company", "1")
			.resources(http("request_7")
			.get("/assets/stylesheets/bootstrap.min.css"),
            http("request_8")
			.get("/assets/stylesheets/main.css")))
		.pause(1)
		.exec(http("SEARCH COMPUTER")
			.get("/computers?f=Edu1005")
			.headers(headers_0)
			.resources(http("request_10")
			.get("/assets/stylesheets/bootstrap.min.css"),
            http("request_11")
			.get("/assets/stylesheets/main.css")))
		.pause(1)
		.exec(http("VIEW COMPUTER DETAILS")
			.get("/computers/878")
			.headers(headers_0))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}