package com.keith.fs.airport.command

import com.keith.fs.airport.domain.Airport
import com.keith.fs.airport.domain.Failure

class DeleteAirport extends Command {
      
	def run(identifier:String) = {
      dao.delete(identifier)
    }
}