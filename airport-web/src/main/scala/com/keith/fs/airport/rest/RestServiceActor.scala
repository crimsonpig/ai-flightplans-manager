package com.keith.fs.airport.rest

import akka.actor.Actor

class RestServiceActor extends Actor with RestService {
	implicit def actorRefFactory = context
	
	def receive = runRoute(rest)
}

