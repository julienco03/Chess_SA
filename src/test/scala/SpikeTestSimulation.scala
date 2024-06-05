package simulations

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import concurrent.duration.DurationInt

class SpikeTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8000") // Replace with your base URL
    // .baseUrl("http://0.0.0.0:8000") // Replace with your base URL
    .inferHtmlResources()

  val scn = scenario("Spike Test Scenario")
    .exec(http("save spike").get("/chess/save"))

  setUp(
    scn.inject(
      nothingFor(5.seconds),
      atOnceUsers(50), // Spike with 100 users instantly
      nothingFor(10.seconds), // Pause for 10 seconds
      atOnceUsers(100), // Spike with 200 users instantly
      nothingFor(20.seconds), // Pause for 20 seconds
      atOnceUsers(300) // Final spike with 500 users instantly
    )
  ).protocols(httpProtocol)
}
