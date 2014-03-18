package com.keith.fs.airport.command

import com.keith.fs.airport.domain.AirportSearchParameters

class SearchCommand extends Command {
	def run(params: AirportSearchParameters) = {
      dao.search(params)
    }
}