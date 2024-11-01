name := "spark-essentials"

version := "0.2"

scalaVersion := "2.13.12"

// Versions for dependencies
val sparkVersion = "3.5.0"
val postgresVersion = "42.6.0"
val log4jVersion = "2.20.0"

// Add necessary resolvers
resolvers += "Maven Central" at "https://repo1.maven.org/maven2/"

// Dependencies
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  // Logging
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
  // PostgreSQL for DB connectivity
  "org.postgresql" % "postgresql" % postgresVersion,
  // MySQL connector
  "mysql" % "mysql-connector-java" % "8.0.33",
  "org.apache.hadoop" % "hadoop-client" % "3.3.4" % "provided",
  // ScalaTest for testing
  "org.scalatest" %% "scalatest" % "3.2.10" % Test
)
