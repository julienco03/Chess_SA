package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class StressTestSimulation extends Simulation {

  // Define the HTTP protocol configuration
  val httpProtocol = http
    .baseUrl("http://localhost:8000") // Replace with your base URL
    // .baseUrl("http://0.0.0.0:8000") // Replace with your base URL
    .userAgentHeader("Gatling Simulation")
  val userAmount = 1300
  val fullDuration = 60.seconds

  // Define the scenario
  val scn = scenario("Stress Test Simulation")
    .exec(
      http("save stress")
        .get("/chess/save") // Replace with your endpoint
        .check(status.is(200))
    )

  // Setup the simulation
  setUp(scn.inject(stressPeakUsers(userAmount).during(fullDuration))).protocols(httpProtocol)
}
