name := "sbt-eclipse-test"

version := "1.0"

scalaVersion := "2.10.3"

resolvers ++= Seq(
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)

scalacOptions ++= Seq("-deprecation","-feature")

libraryDependencies ++= Seq(
    "io.spray" % "spray-can" % "1.1-M8",
    "io.spray" % "spray-http" % "1.1-M8",
    "io.spray" % "spray-routing" % "1.1-M8",
    "net.liftweb" %% "lift-json" % "2.5.1",
    "com.typesafe.akka" %% "akka-actor" % "2.1.4",
    "com.typesafe.akka" %% "akka-slf4j" % "2.1.4",
    "com.typesafe.slick" %% "slick" % "2.0.0",
    "mysql" % "mysql-connector-java" % "5.1.25",
    "ch.qos.logback" % "logback-classic" % "1.0.13"
)



