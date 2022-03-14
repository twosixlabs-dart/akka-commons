package com.twosixlabs.dart.pipeline.utils

import com.twosixlabs.dart.pipeline.utils.ConfigUtils._
import com.typesafe.config.Config
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConverters._

object Kafka {

    private lazy val LOG : Logger = LoggerFactory.getLogger( getClass )

    private val PRODUCER_DEFAULTS : Map[ String, String ] = Map( "key.serializer" -> classOf[ StringSerializer ].getCanonicalName,
                                                                 "key.deserializer" -> classOf[ StringDeserializer ].getCanonicalName,
                                                                 "value.serializer" -> classOf[ StringSerializer ].getCanonicalName,
                                                                 "value.deserializer" -> classOf[ StringDeserializer ].getCanonicalName )

    private val CONSUMER_DEFAULTS : Map[ String, String ] = PRODUCER_DEFAULTS // they're the same for now

    def newProducer[ K, V ]( bootstrap : String, config : Config ) : KafkaProducer[ K, V ] = newProducer( bootstrap, configToMap( config ) )

    def newProducer[ K, V ]( bootstrap : String, props : Map[ String, String ] ) : KafkaProducer[ K, V ] = {
        val producerProps : Map[ String, String ] = createKafkaConfig( props, PRODUCER_DEFAULTS + ( "bootstrap.servers" -> bootstrap ) )
        if ( LOG.isDebugEnabled ) logConsumerInfo( producerProps )

        new KafkaProducer[ K, V ]( producerProps.asJava.asInstanceOf[ java.util.Map[ String, AnyRef ] ] )
    }

    def newConsumer[ K, V ]( bootstrap : String, config : Config ) : KafkaConsumer[ K, V ] = newConsumer( bootstrap, configToMap( config ) )

    def newConsumer[ K, V ]( bootstrap : String, props : Map[ String, String ] ) : KafkaConsumer[ K, V ] = {
        val consumerProps : Map[ String, String ] = createKafkaConfig( props, CONSUMER_DEFAULTS ) + ( "bootstrap.servers" -> bootstrap )
        if ( LOG.isDebugEnabled ) logConsumerInfo( consumerProps )

        val consumer = new KafkaConsumer[ K, V ]( consumerProps.asJava.asInstanceOf[ java.util.Map[ String, AnyRef ] ] )
        consumer.subscribe( List( props( "topic" ) ).asJava )
        consumer
    }

    private def createKafkaConfig( props : Map[ String, String ],
                                   defaults : Map[ String, String ] ) : Map[ String, String ] = {
        overlay( props, defaults )
    }

    private def logConsumerInfo( props : Map[ String, String ] ) : Unit = {
        LOG.debug( "using the following kafka properties..." )
        props.foreach( kv => LOG.debug( s"${kv._1} = ${kv._2}" ) )
    }
}
