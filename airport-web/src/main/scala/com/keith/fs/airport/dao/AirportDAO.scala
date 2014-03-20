package com.keith.fs.airport.dao

//import scala.slick.session.Database
import com.keith.fs.airport.domain._
import com.keith.fs.airport.config.Configuration
import java.sql._
import scala.Some
//import scala.slick.driver.MySQLDriver.simple.Database.threadLocalSession
//import scala.slick.jdbc.JdbcBackend.Database.dynamicSession
import scala.slick.driver.MySQLDriver.simple._
import scala.slick.jdbc.meta.MTable

class AirportDAO extends Configuration {
	private val db = Database.forURL(url = "jdbc:mysql://%s:%d/%s".format(dbHost,dbPort,dbName), 
	    user = dbUser, password = dbPassword, driver = "com.mysql.jdbc.Driver")
	    
//	db.withSession {
//	  if(MTable.getTables("airports").list.isEmpty){
//	    Airports.ddl.create
//	  }
//	}
	
	private val airportsTq = TableQuery[Airports] 

	def create(airport: Airport): Either[Failure, Airport] = {
	  try{
	    val ident = db.withSession { implicit session => 
		  airportsTq.insert(airport)
		  airport.identifier
		}
	    Right(airport.copy(identifier = ident))
	  } catch {
	    case e: SQLException =>
	      Left(databaseError(e))
	  }
	}
	
	def update(ident: String, airport: Airport): Either[Failure, Airport] = {
	  try {
		db.withSession { implicit session => 
		  airportsTq.where(_.identifier === ident) update airport.copy(identifier = ident) match {
		    case 0 => Left(notFoundError(ident))
		    case _ => Right(airport.copy(identifier = ident))
		  }
		}
	  } catch {
	    case e: SQLException =>
	      Left(databaseError(e))
	  }
	}
	
	def delete(ident: String): Either[Failure, Airport] = {
	  try{
		db.withTransaction {  implicit session => 
		  val query = airportsTq.where(_.identifier === ident)
		  val airports = query.run.asInstanceOf[Vector[Airport]]
		  airports.size match {
		    case 0 =>
		      Left(notFoundError(ident))
		    case _ => {
		      query.delete
		      Right(airports.head)
		    }
		  }
		}
	  } catch {
	    case e: SQLException =>
	      Left(databaseError(e))
	  }
	}
	
	def get(ident: String): Either[Failure, Airport] = {
	  try{
	    db.withSession { implicit session => 
//			airportsTq.findByIdentifier(ident).firstOption match {
		  val airports = airportsTq.where(_.identifier === ident).run
		  airports.size match {
		    case 0 =>
		      Left(notFoundError(ident))
		    case _ => {
		      Right(airports.head)
		    }
		  }
		}
	  } catch {
	      case e: SQLException =>
	      Left(databaseError(e))
	  }
		  
	}
	
	def search(params: AirportSearchParameters): Either[Failure, List[Airport]] = {
	  
	  try{
	      db.withSession { implicit session => 
		  val query = for {
		    airport <- airportsTq if {
		      Seq(
		        params.identifier.map(airport.identifier is _),
		    	params.latitude.map(airport.latitude is _),
		    	params.longitude.map(airport.longitude is _),
		    	params.elevation.map(airport.elevation is _)
		      ).flatten match {
		        case Nil => LiteralColumn(true)
		        case seq => seq.reduce(_ && _)
		      }
		    }
		  } yield airport
		  
		  Right(query.run.toList)
	   }
	  } catch {
	      case e: SQLException =>
	        Left(databaseError(e))
	  }

	}

	protected def databaseError(e: SQLException) = 
	  Failure("%d: %s".format(e.getErrorCode, e.getMessage), FailureType.DatabaseFailure)
	  
	protected def notFoundError(airportId: String) = 
	  Failure("Airport with identifier %s does not exist".format(airportId), FailureType.NotFound)
}
