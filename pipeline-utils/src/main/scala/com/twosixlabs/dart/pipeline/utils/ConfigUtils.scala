package com.twosixlabs.dart.pipeline.utils

import com.typesafe.config.Config

import scala.collection.JavaConverters._
import scala.collection.mutable

object ConfigUtils {

    def configToMap( config : Config ) : Map[ String, String ] = {
        config
          .entrySet()
          .asScala
          .map( pair => (pair.getKey, pair.getValue.unwrapped().toString) )
          .toMap
    }

    /**
     * merge a map with a set of defaults and overrides
     *
     * @param properties - map of properties
     * @param defaults   - default values, existing properties will be overridden by `propreties`
     * @return
     */
    def overlay( properties : Map[ String, String ], defaults : Map[ String, String ] ) : Map[ String, String ] = {
        val results : mutable.Map[ String, String ] = mutable.Map() ++= defaults
        properties.foreach( kv => results += ( kv._1 -> kv._2 ) )

        results.toMap
    }

}
