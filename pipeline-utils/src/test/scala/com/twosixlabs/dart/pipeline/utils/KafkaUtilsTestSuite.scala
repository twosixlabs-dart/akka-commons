package com.twosixlabs.dart.pipeline.utils

import com.twosixlabs.dart.pipeline.utils.test.Shared.CONFIG
import com.twosixlabs.dart.test.base.StandardTestBase3x
import net.manub.embeddedkafka.{EmbeddedKafka, EmbeddedKafkaConfig}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.{Deserializer, Serdes, Serializer}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext

class KafkaUtilsTestSuite extends StandardTestBase3x with EmbeddedKafka {
    private implicit val EMBEDDED_KAFKA_CONFIG = EmbeddedKafkaConfig( kafkaPort = 6308, zooKeeperPort = 6309 )
    private implicit val serializer : Serializer[ String ] = Serdes.String.serializer()
    private implicit val deserializer : Deserializer[ String ] = Serdes.String.deserializer()

    implicit val executionContext : ExecutionContext = scala.concurrent.ExecutionContext.global

    private val BOOTSTRAP_SERVERS : String = CONFIG.getString( "kafka.bootstrap.servers" )

    "Kafka Provider" should "create a Consumer with specified properties" in { // TODO - testing consumers is a PITA for some reason
        //@formatter:off
        withRunningKafka {
            val consumer : KafkaConsumer[ Integer, Double ] = Kafka.newConsumer[ Integer, Double ]( BOOTSTRAP_SERVERS, CONFIG.getConfig( "kafka.consumer" ) )
            consumer.subscription() shouldBe Set( "test-topic-1" ).asJava
        }
        //@formatter:on
    }

    "Kafka Producer" should "create a Producer with the specified properties" in {
        //@formatter:off
        withRunningKafka {
            val producer : KafkaProducer[ String, String ] = Kafka.newProducer[ String, String ]( BOOTSTRAP_SERVERS, CONFIG.getConfig( "kafka.producer" ) )

            producer.send( new ProducerRecord[ String, String ]( "producer.test", "key", "value" ) )

            val result = consumeFirstKeyedMessageFrom( "producer.test" )
            result._1 shouldBe "key"
            result._2 shouldBe "value"
        }
        //@formatter:on
    }

}
