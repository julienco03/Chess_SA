package simulations

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import concurrent.duration.DurationInt

class EnduranceTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8000") // Replace with your base URL
    // .baseUrl("http://0.0.0.0:8000") // Replace with your base URL
    .inferHtmlResources()

  val userAmount = 1300
  val fullDuration = 60.seconds

  val scn = scenario("Load Test Scenario")
    .exec(http("save load").get("/chess/save"))

   setUp(scn.inject(rampUsers(userAmount).during(fullDuration))).protocols(httpProtocol)
}
