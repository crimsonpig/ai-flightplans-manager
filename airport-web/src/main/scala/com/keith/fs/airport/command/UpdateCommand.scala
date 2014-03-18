package com.keith.fs.airport.command

import com.keith.fs.airport.domain._

class UpdateAirport extends Command {

	def run(identifier:String, airport:Airport) = {
      dao.update(identifier, airport)
    }
}