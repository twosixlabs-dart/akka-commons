package com.twosixlabs.dart.pipeline.utils

import com.twosixlabs.dart.pipeline.utils.test.Shared.CONFIG
import com.twosixlabs.dart.test.base.StandardTestBase3x

class ConfigUtilsTestSuite extends StandardTestBase3x {

    "Config Utils" should "work" in {
        val config = CONFIG.getConfig( "kafka.test-consumer-1" )

        val configMap : Map[ String, String ] = ConfigUtils.configToMap( config )

        configMap shouldBe Map( "topic" -> "test-topic-2",
                                "group.id" -> "test",
                                "value.deserializer" -> "org.apache.kafka.common.serialization.IntegerDeserializer" )

        val defaults = Map( "my.default" -> "default",
                            "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer" )

        val overlaid = ConfigUtils.overlay( configMap, defaults )

        overlaid shouldBe Map( "my.default" -> "default",
                               "topic" -> "test-topic-2",
                               "group.id" -> "test",
                               "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
                               "value.deserializer" -> "org.apache.kafka.common.serialization.IntegerDeserializer" )
    }

}
