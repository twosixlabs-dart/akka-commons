package com.twosixlabs.dart.pipeline.utils.test

import com.typesafe.config.{Config, ConfigFactory}

object Shared {

    val CONFIG : Config = ConfigFactory.parseResources( "test.conf" )
}
