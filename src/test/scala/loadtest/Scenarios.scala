package loadtest

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._

object Scenarios {

    val rampUpTimeSecs = 20

    /*
    *	HTTP scenarios
    */
    // Browse
    val browse_guids = csv("deployment.csv").circular

    val scn_JsonCreate = scenario("Deployment")
            .during(Conf.duration) {
            feed(browse_guids)
            .exec(http("Deployment")
                .post("/api/v2/deployments/")
                .body(StringBody("""{
                                    "deployment_id": "${deploymentId}",
                                    "deployment_name": "redis",
                                    "placement": {
                                        "pool_id": "default"
                                    },
                                    "quantities": {
                                        "redis": 2
                                    },
                                    "stack": {
                                        "content": "redis:\n  image: \"redis:latest\"\n",
                                        "service_id": "redis",
                                        "service_name": "${deploymentName}",
                                        "subtype": "service"
                                    }
                                    }
                                    """)).asJSON
                .headers(Headers.http_header)
                .check(status.is(200))
            )
            .pause(2)
            .exec(http("StartDeployment")
                .post("/api/v2/deployments/${deploymentId}/start")
                .headers(Headers.http_header)
                .check(status.is(200))
            )
            .pause(2)
            .exec(http("ListDeployment")
                .get("/api/v2/deployments/${deploymentId}")
                .headers(Headers.http_header)
                .check(status.is(200))
                .check(jsonPath("$..deployment_id").is("${deploymentId}"))
            )
            .pause(2)
            .exec(http("ListDepContainers")
                .get("/api/v2/deployments/${deploymentId}/containers/")
                .headers(Headers.http_header)
                .check(status.is(200))
                .check(jsonPath("$..state").is("Running"))
            )
            .pause(2)
            .exec(http("StopDeployment")
                .post("/api/v2/deployments/${deploymentId}/stop")
                .headers(Headers.http_header)
                .check(status.is(200))
            )
            .pause(5)
            .exec(http("DeleteDeployment")
                .delete("/api/v2/deployments/${deploymentId}")
                .headers(Headers.http_header)
                .check(status.is(200))
            )
        }


        val scn_JsonCreateUsingFile = scenario("DeploymentUsingFile")
                .during(Conf.duration) {
                feed(browse_guids)
                .exec(http("Deployment")
                    .post("/api/v2/deployments/")
                    .body(ElFileBody("src/test/resources/json/test.json")).asJSON
                    .headers(Headers.http_header)
                    .check(status.is(200))
                )
                .pause(5)
                .exec(http("DeleteDeployment")
                    .delete("/api/v2/deployments/${deploymentId}")
                    .headers(Headers.http_header)
                    .check(status.is(200))
                )
        }
}
