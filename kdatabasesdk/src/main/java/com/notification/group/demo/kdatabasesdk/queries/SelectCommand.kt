package com.notification.group.demo.kdatabasesdk.queries

import android.database.Cursor

abstract class  SelectCommand {
    abstract fun onResultSet(cursor: Cursor)
}