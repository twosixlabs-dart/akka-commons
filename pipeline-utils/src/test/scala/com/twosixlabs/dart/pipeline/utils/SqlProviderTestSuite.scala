package com.twosixlabs.dart.pipeline.utils

import com.twosixlabs.dart.pipeline.utils.test.Shared.CONFIG
import com.twosixlabs.dart.test.base.StandardTestBase3x

class SqlProviderTestSuite extends StandardTestBase3x {

    "SQL Client Provider" should "create a usable SQL client from configuration" in {
        val client = SqlProvider.newClient( CONFIG.getConfig( "sql" ) )
        val connection = client.connect()

        val tableSql =
            s"""CREATE TABLE sql_test (
               |    id TEXT
               |);""".stripMargin

        client.executeUpdate( tableSql, connection )

        val insertSql = "INSERT INTO sql_test VALUES('abc123')"
        client.executeUpdate( insertSql, connection )

        val selectSql = "SELECT COUNT(*) as count FROM sql_test"
        val results = client.executeQuery( selectSql, connection )

        while ( results.next() ) {
            results.getInt( "count" ) shouldBe 1
        }

        connection.close()
    }

}
