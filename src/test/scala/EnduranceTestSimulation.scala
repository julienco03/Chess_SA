package simulations

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import concurrent.duration.DurationInt

class EnduranceTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8000") // Replace with your base URL
    // .baseUrl("http://0.0.0.0:8000") // Replace with your base URL
    .inferHtmlResources()

  val scn = scenario("Endurance Test Scenario")
    .exec(http("save endurance").get("/chess/save"))

  setUp(
    scn.inject(
      constantUsersPerSec(50).during(2.minutes) // Maintain 50 users per second for 2 hours
    )
  ).protocols(httpProtocol)
}
