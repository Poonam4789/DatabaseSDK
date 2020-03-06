package com.notification.group.demo.kdatabasesdk

import android.content.Context
import com.notification.group.demo.kdatabasesdk.queries.ExecuteQuery
import com.notification.group.demo.kdatabasesdk.queries.SelectCommand
import java.util.*

object DatabaseManager {

    lateinit var _dbhelper: DBHelper

    fun initialize(context: Context) {

        _dbhelper = DBHelper(context, QueryBuilder(context))
        _dbhelper.initDB()

    }

    @Synchronized
    fun executeQuery(query: ExecuteQuery): Long {
        return _dbhelper.executeQuery(query)
    }

    @Synchronized
    fun executeQuery(insertCommands: ArrayList<out ExecuteQuery>): Long {
        return _dbhelper.executeQuery(insertCommands)
    }

    @Synchronized
    fun fillData(query: String, SelectCommand: SelectCommand): SelectCommand {
        return _dbhelper.fillData(query, SelectCommand)
    }
}