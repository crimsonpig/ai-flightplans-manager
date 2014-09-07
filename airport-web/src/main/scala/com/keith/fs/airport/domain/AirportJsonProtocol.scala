package com.keith.fs.airport.domain

import spray.json.DefaultJsonProtocol

object AirportJsonProtocol extends DefaultJsonProtocol {
	implicit val airportFormat = jsonFormat4(Airport)
}