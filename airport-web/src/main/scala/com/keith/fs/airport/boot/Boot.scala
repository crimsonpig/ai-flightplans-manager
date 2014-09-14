package com.keith.fs.airport.boot

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http
import com.keith.fs.airport.rest.RestServiceActor

object Boot extends App with Configuration {
	implicit val system = ActorSystem("airport-web")
	
	val restService = system.actorOf(Props[RestServiceActor], "rest-endpoint")
	
	IO(Http) ! Http.Bind(restService, serviceHost, servicePort)
}