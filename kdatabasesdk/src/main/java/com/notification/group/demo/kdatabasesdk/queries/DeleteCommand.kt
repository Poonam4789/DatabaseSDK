package com.notification.group.demo.kdatabasesdk.queries

import android.database.sqlite.SQLiteDatabase

class DeleteCommand(tableName: String, whereClause: String) : ExecuteQuery() {
    private val _tableName: String = tableName
    private val _whereClause: String = whereClause

    override fun execute(db: SQLiteDatabase): Long {
        return db.delete(_tableName, _whereClause, null).toLong()
    }
}