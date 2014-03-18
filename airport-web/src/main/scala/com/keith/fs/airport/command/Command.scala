package com.keith.fs.airport.command


import com.keith.fs.airport.dao.AirportDAO

trait Command {
  protected val dao = new AirportDAO
}