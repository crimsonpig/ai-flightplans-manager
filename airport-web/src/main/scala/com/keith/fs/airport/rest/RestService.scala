package com.keith.fs.airport.rest

import akka.event.slf4j.SLF4JLogging
import com.keith.fs.airport.dao.AirportDAO
import com.keith.fs.airport.domain._
import scala.Some
import spray.http._
import spray.httpx.unmarshalling._
import spray.routing._
import java.text.SimpleDateFormat
import java.util.Date
import com.keith.fs.airport.command._
import org.json4s.Formats
import org.json4s.DefaultFormats
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonAST.JObject
import spray.httpx.Json4sSupport

trait RestService extends HttpService with SLF4JLogging with Json4sSupport {
//	val airportService = new AirportDAO
	val createAirport = new CreateAirport
	val retrieveAirport = new RetrieveAirport
	val deleteAirport = new DeleteAirport 
	val updateAirport  = new UpdateAirport
	
	implicit val executionContext = actorRefFactory.dispatcher
	implicit val json4sFormats : Formats = DefaultFormats.withBigDecimal
//	implicit val unmarshaller = Unmarshaller
	
//	implicit val customRejectionHandler = RejectionHandler {
//	  case rejections => mapHttpResponse {
//	    response => 
//	      response.withEntity(HttpEntity(ContentType(MediaTypes.`application/json`),
//	          compact(render("error" -> response.entity.asString))))
//	          
//	  }{
//	    RejectionHandler.Default(rejections)
//	  }
//	}
	
	val rest = respondWithMediaType(MediaTypes.`application/json`){
	  path("airport") {
	    post {
	    	entity(as[JObject]) { airportJs => 
	    	  	complete {
	    	  	  val airport = airportJs.extract[Airport]
	    	  	  log.debug("Creating airport: %s".format(airport))
	    	  	  createAirport.run(airport)
	    	  	}
	    	}
	    } 
	  } ~ 
	  path("airport" / Segment){
	    ident => 
	     /* put {
	        entity(Unmarshaller(MediaTypes.`application/json`){
	          case httpEntity: HttpEntity => 
	            read[Airport](httpEntity.asString(HttpCharsets.`UTF-8`))
	        }) {
	          airport: Airport =>
	            ctx: RequestContext => 
	              handleRequest(ctx){
	                log.debug("Updating airport with id %s: %s".format(ident, airport))
	                updateAirport.run(ident, airport)
//	                airportService.update(ident, airport)
	              }
	        }
	      } ~ */ 
	      delete {
	        ctx: RequestContext => 
	          handleRequest(ctx){
	            log.debug("Deleting airport with id %s".format(ident))
	            deleteAirport.run(ident)
	          }
	      } ~ 
	      get {
	        ctx: RequestContext => 
	          handleRequest(ctx){
	            log.debug("Retrieving airport with id %s".format(ident))
	            retrieveAirport.run(ident)
	          }
	      }
	  }
	}

	protected def handleRequest(ctx: RequestContext, successCode: StatusCode = StatusCodes.OK)(action: => Either[Failure, Airport]) {
	  action match {
	    case Right(result: Airport) => 
	      ctx.complete(successCode, result)
	    case Left(error: Failure) =>
	      ctx.complete(error.getStatusCode, error.message)
	    case _ =>
	      ctx.complete(StatusCodes.InternalServerError)
	  }
	}
}