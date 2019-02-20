package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Execution
  * "mvn gatling:test -Dgatling.simulationClass=computerdatabase.ReactiveSimulation"
  */
class ReactiveSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8082")
    .contentTypeHeader("application/json")

  // https://gatling.io/docs/current/general/scenario/

  val normalScn = scenario("Normal Scenario")
    .exec(http("normal case")
      .get("/normal"))

  val slowScn = scenario("Slow Scenario")
    .exec(http("slow case")
      .get("/slow"))

  // https://gatling.io/docs/current/general/simulation_setup/
  // run 2 scenarios simultaneously
  setUp(
    normalScn.inject(constantUsersPerSec(100) during (10))
      .disablePauses
      .protocols(httpProtocol),
    slowScn.inject(constantUsersPerSec(100) during (10))
      .disablePauses
      .protocols(httpProtocol)
  )
}
