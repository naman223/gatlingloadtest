package loadtest

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

object Conf {
	var users = System.getProperty("users", "30").toInt
	val baseUrl = System.getProperty("baseUrl", "http://localhost:6767")
	var httpConf = http.baseURL(baseUrl)
	var duration = System.getProperty("duration", "240").toInt
}
