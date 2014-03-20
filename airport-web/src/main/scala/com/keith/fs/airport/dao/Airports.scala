package com.keith.fs.airport.dao
import scala.slick.driver.MySQLDriver.simple._
import com.keith.fs.airport.domain.Airport

//object Gates extends Table[(Int, String)]("gates"){
//  def id = column[Int]("id", O.PrimaryKey)
//  def gate = column[String]("gate")
//}

class Airports(tag: Tag) extends Table[Airport](tag, "airports"){
  
  def identifier = column[String]("ident", O.PrimaryKey)
  def latitude = column[Double]("lat")
  def longitude = column[Double]("lon")
  def elevation = column[Int]("elev")
//  def gateId = column[Int]("gate_id")
//  def gate = foreignKey("gate_fk", gateId, Gates)(_.id)
  
  def * = (identifier, latitude, longitude, elevation) <> (Airport.tupled, Airport.unapply)
}

