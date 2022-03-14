import sbt._

object Dependencies {

    val slf4jVersion = "1.7.20"
    val logbackVersion = "1.2.9"

    val scalaTestVersion = "3.0.5"
    val embeddedKafkaVersion = "2.2.0"
    val mockitoVersion = "1.16.0"

    val betterFilesVersion = "3.8.0"

    val akkaVersion = "2.6.9"
    val operationsVersion = "3.0.19"
    val kafkaVersion = "2.2.1"
    val dartCommonsVersion = "3.0.307"

    val akka = Seq( "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
                    "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test )

    val kafka = Seq( "org.apache.kafka" %% "kafka" % kafkaVersion,
                     "org.apache.kafka" % "kafka-clients" % kafkaVersion )

    val operations = Seq( "com.twosixlabs.dart.operations" %% "status-client" % operationsVersion )

    val dartCommons = Seq( "com.twosixlabs.dart" %% "dart-json" % dartCommonsVersion,
                           "com.twosixlabs.dart" %% "dart-test-base" % dartCommonsVersion % Test )

    val logging = Seq( "org.slf4j" % "slf4j-api" % slf4jVersion,
                       "ch.qos.logback" % "logback-classic" % logbackVersion )

    val betterFiles = Seq( "com.github.pathikrit" %% "better-files" % betterFilesVersion )

    val unitTesting = Seq( "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
                       "org.mockito" %% "mockito-scala-scalatest" % mockitoVersion % Test )

    val embeddedKafka = Seq( "io.github.embeddedkafka" %% "embedded-kafka" % embeddedKafkaVersion % Test,
                             "io.github.embeddedkafka" %% "embedded-kafka-streams" % embeddedKafkaVersion % Test,
                             "jakarta.ws.rs" % "jakarta.ws.rs-api" % "2.1.2" % Test ) //https://github.com/sbt/sbt/issues/3618


}
