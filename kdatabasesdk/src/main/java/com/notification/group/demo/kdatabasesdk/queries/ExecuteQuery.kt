package com.notification.group.demo.kdatabasesdk.queries

import android.database.sqlite.SQLiteDatabase

abstract class ExecuteQuery {
    abstract fun execute(db: SQLiteDatabase): Long
}
