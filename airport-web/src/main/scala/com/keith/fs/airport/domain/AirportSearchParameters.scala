package com.keith.fs.airport.domain

case class AirportSearchParameters( identifier: Option[String] = None, 
									latitude: Option[Double] = None, 
									longitude: Option[Double] = None, 
									elevation: Option[Int] = None)
							