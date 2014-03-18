package com.keith.fs.airport.command

import com.keith.fs.airport.dao.AirportDAO
import com.keith.fs.airport.domain.Airport
import com.keith.fs.airport.domain.Failure

class RetrieveAirport extends Command {

	def run(identifier: String) = {
      dao.get(identifier)
    }
}