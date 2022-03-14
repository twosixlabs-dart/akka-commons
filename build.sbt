import Dependencies._
import sbt._

organization in ThisBuild := "com.twosixlabs.dart"
name := "dart-pipeline-commons"
scalaVersion in ThisBuild := "2.12.7"

resolvers in ThisBuild ++= Seq( "Spray IO Repository" at "https://repo.spray.io/",
                                "Maven Central" at "https://repo1.maven.org/maven2/",
                                "JCenter" at "https://jcenter.bintray.com",
                                "Local Ivy Repository" at s"file://${System.getProperty( "user.home" )}/.ivy2/local" )

publishMavenStyle := true

lazy val root = ( project in file( "." ) ).aggregate( utils ) // sparkUtils removed...

lazy val utils = ( project in file( "pipeline-utils" ) ).settings( libraryDependencies ++= akka
                                                                                           ++ kafka
                                                                                           ++ operations
                                                                                           ++ dartCommons
                                                                                           ++ betterFiles
                                                                                           ++ unitTesting
                                                                                           ++ embeddedKafka,
                                                                   excludeDependencies ++= Seq( ExclusionRule( "org.slf4j", "slf4j-log4j12" ),
                                                                                  ExclusionRule( "org.slf4j", "log4j-over-slf4j" ),
                                                                                  ExclusionRule( "log4j", "log4j" ),
                                                                                  ExclusionRule( "org.apache.logging.log4j", "log4j-core" ) ) )
test in publish := {}

javacOptions in ThisBuild ++= Seq( "-source", "8", "-target", "8" )
scalacOptions in ThisBuild += "-target:jvm-1.8"
