package com.notification.group.demo.kdatabasesdk.queries

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class UpdateCommand(tableName: String, whereClause: String, contentValues: ContentValues) :
    ExecuteQuery() {

    private var _tableName: String = tableName
    private var _contentValues: ContentValues = contentValues
    private var _whereClause: String = whereClause

    constructor(tableName: String, whereClause: String) : this(
        tableName,
        whereClause,
        ContentValues()
    ) {
        _tableName = tableName
        _whereClause = whereClause
        _contentValues = ContentValues()
    }

    fun update(column: String, value: String): UpdateCommand {
        _contentValues.put(column, value)
        return this
    }

    fun update(column: String, value: Int): UpdateCommand {
        _contentValues.put(column, value)
        return this
    }

    fun update(column: String, value: Long): UpdateCommand {
        _contentValues.put(column, value)
        return this
    }

    fun update(column: String, value: Float): UpdateCommand {
        _contentValues.put(column, value)
        return this
    }

    fun update(column: String, value: Double): UpdateCommand {
        _contentValues.put(column, value)
        return this
    }

    fun update(column: String, value: Boolean): UpdateCommand {
        _contentValues.put(column, value)
        return this
    }

    fun update(column: String, value: ByteArray): UpdateCommand {
        _contentValues.put(column, value)
        return this
    }

    override fun execute(db: SQLiteDatabase): Long {
        return db.update(_tableName, _contentValues, _whereClause, null).toLong(); }
}