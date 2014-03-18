package com.keith.fs.airport.command

import com.keith.fs.airport.domain.Airport
import com.keith.fs.airport.domain.Failure
import com.keith.fs.airport.dao.AirportDAO


class CreateAirport extends Command {

	def run(airport: Airport): Either[Failure, Airport] = {
    	dao.create(airport)
	}
}