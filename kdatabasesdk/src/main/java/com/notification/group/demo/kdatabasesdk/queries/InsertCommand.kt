package com.notification.group.demo.kdatabasesdk.queries

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class InsertCommand(tableName: String, contentValues: ContentValues) : ExecuteQuery() {
    private var _tableName: String? = null
    private var _contentValues: ContentValues? = null

    constructor(tableName: String) : this(tableName, ContentValues()) {
        _tableName = tableName
        _contentValues = ContentValues()
    }

    fun insert(column: String, value: String): InsertCommand {
        _contentValues?.put(column, value)
        return this
    }

    fun insert(column: String, value: Int): InsertCommand {
        _contentValues?.put(column, value)
        return this
    }

    fun insert(column: String, value: Long): InsertCommand {
        _contentValues?.put(column, value)
        return this
    }

    fun insert(column: String, value: Float): InsertCommand {
        _contentValues?.put(column, value)
        return this
    }

    fun insert(column: String, value: Double): InsertCommand {
        _contentValues?.put(column, value)
        return this
    }

    fun insert(column: String, value: Boolean): InsertCommand {
        _contentValues?.put(column, value)
        return this
    }

    fun insert(column: String, value: ByteArray): InsertCommand {
        _contentValues?.put(column, value)
        return this
    }

    override fun execute(db: SQLiteDatabase): Long {
        return db.insert(_tableName, null, _contentValues).toLong()
    }
}