package com.twosixlabs.dart.pipeline.utils

import com.twosixlabs.dart.sql.SqlClient
import com.typesafe.config.Config

import scala.util.control.Exception.allCatch

object SqlProvider {

    def newClient( config : Config ) : SqlClient = {
        val engine = config.getString( "engine" )

        val dbConf = config.getConfig( engine )
        val name : String = dbConf.getString( "name" )
        val host : Option[ String ] = allCatch.opt( dbConf.getString( "host" ) )
        val port : Option[ Int ] = allCatch.opt( dbConf.getInt( "port" ) )
        val user : Option[ String ] = allCatch.opt( dbConf.getString( "user" ) )
        val password : Option[ String ] = allCatch.opt( dbConf.getString( "password" ) )

        engine match {
            case "h2" => SqlClient.newClient( engine, name, null, -1, None, None )
            case "postgresql" => SqlClient.newClient( engine, name, host.get, port.get, user, password )
            case other => throw new IllegalArgumentException( s"${other} is not a supported SQL engine type" )
        }
    }

}
