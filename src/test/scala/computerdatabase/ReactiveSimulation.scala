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
  val scn = scenario("Simple Loading")
    .exec(http("slow case")
      .get("/slow"))
    .exec(http("normal case")
      .get("/normal"))

  // https://gatling.io/docs/current/general/simulation_setup/
  setUp(
    scn.inject(
      constantUsersPerSec(100) during (10)
    )
      .disablePauses
      .protocols(httpProtocol)
  )
}
