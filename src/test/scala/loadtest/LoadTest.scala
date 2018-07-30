package loadtest

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._

class LoadTest extends Simulation {

    /* Place for arbitrary Scala code that is to be executed before the simulation begins. */
    before {
        println("***** My simulation is about to begin! *****")
    }

    /* Place for arbitrary Scala code that is to be executed after the simulation has ended. */
    after {
        println("***** My simulation has ended! ******")
    }

    setUp (
        /* rampUsers(nbUsers) over(duration): Injects a given number of users with a linear ramp over a given duration. */
        Scenarios.scn_JsonCreate
            .inject(rampUsers(Conf.users) over (Scenarios.rampUpTimeSecs seconds))
            .protocols(Conf.httpConf)

        /* same above scenario but using Json File to Deploy. */
        /* Scenarios.scn_JsonCreateUsingFile
            .inject(rampUsers(Conf.users) over (Scenarios.rampUpTimeSecs seconds))
            .protocols(Conf.httpConf) */


        /* Scenarios.scn_JsonCreate
            .inject(
              nothingFor(4 seconds), // 1
              atOnceUsers(1), // 2
              rampUsers(3) over (2 seconds), // 3
              constantUsersPerSec(1) during (2 seconds), // 4
              constantUsersPerSec(1) during (2 seconds) randomized, // 5
              rampUsersPerSec(1) to 3 during (3 seconds), // 6
              rampUsersPerSec(1) to 3 during (2 seconds) randomized, // 7
              splitUsers(2) into (rampUsers(1) over (5 seconds)) separatedBy (2 seconds), // 8
              splitUsers(2) into (rampUsers(1) over (5 seconds)) separatedBy atOnceUsers(5), // 9
              heavisideUsers(1) over (500 milliseconds) // 10
            ).protocols(Conf.httpConf) */

    )
    .assertions(
        global.responseTime.max.lte(3000),
        forAll.failedRequests.count.lte(10),
        global.successfulRequests.percent.gte(95),
        global.successfulRequests.percent.gte(95)
    )
}