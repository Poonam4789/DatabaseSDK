package com.notification.group.demo.kdatabasesdk

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.notification.group.demo.kdatabasesdk.queries.ExecuteQuery
import com.notification.group.demo.kdatabasesdk.queries.SelectCommand

class DBHelper(context: Context, queryBuilder: QueryBuilder) :
    SQLiteOpenHelper(context, queryBuilder.getDBName(), null, queryBuilder.getDBVersion()) {
    private val _queryBuilder: QueryBuilder = queryBuilder
    private val _lockObject = Object()

    override fun onCreate(db: SQLiteDatabase?) {
        val queries = _queryBuilder.getDDLQueries()
        for (query in queries) {
            try {
                db?.execSQL(query)
            } catch (ex: Exception) {
                //ex.printStackTrace();
                HandledErrorTracker.registerThrowable(ex)
            }

        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            var queries: ArrayList<String> = _queryBuilder.getDDLChangesQueries(oldVersion)
            for (query in queries) {
                try {
                    db?.execSQL(query)
                } catch (ex: Exception) {
                    //ex.printStackTrace();
                    HandledErrorTracker.registerThrowable(ex)
                }

            }
        }
    }

    fun initDB() {
        synchronized(_lockObject) {
            var db: SQLiteDatabase? = null
            val value: Long = -1
            try {
                db = writableDatabase
                val version = writableDatabase.version
            } catch (ex: SQLiteException) {
                //ex.printStackTrace();
                HandledErrorTracker.registerThrowable(ex)
            } catch (ex: Exception) {
                //ex.printStackTrace();
                HandledErrorTracker.registerThrowable(ex)
            } finally {
                db?.close()
            }
        }
    }

    @Synchronized
    fun executeQuery(queries: java.util.ArrayList<out ExecuteQuery>): Long {
        synchronized(_lockObject) {
            var db: SQLiteDatabase? = null
            var value: Long = -1
            try {
                db = writableDatabase
                db!!.beginTransaction()
                for (query in queries) {
                    value = query.execute(db)
                }
                db.setTransactionSuccessful()
            } catch (ex: SQLiteException) {
                //ex.printStackTrace();
                HandledErrorTracker.registerThrowable(ex)
            } catch (ex: Exception) {
                //ex.printStackTrace();
                HandledErrorTracker.registerThrowable(ex)
            } finally {
                if (db != null) {
                    db.endTransaction()
                    db.close()
                }
            }
            return value
        }
    }

    @Synchronized
    fun executeQuery(query: ExecuteQuery): Long {
        synchronized(_lockObject) {
            var db: SQLiteDatabase? = null
            var value: Long = -1
            try {
                db = writableDatabase
                value = query.execute(db)
            } catch (ex: SQLiteException) {
                //ex.printStackTrace();
                HandledErrorTracker.registerThrowable(ex)
            } catch (ex: Exception) {
                //ex.printStackTrace();
                HandledErrorTracker.registerThrowable(ex)
            } finally {
                db?.close()
            }
            return value
        }
    }

    @Synchronized
    fun fillData(query: String, SelectCommand: SelectCommand): SelectCommand {
        synchronized(_lockObject) {
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                db = readableDatabase
                cursor = db!!.rawQuery(query, null)
                SelectCommand.onResultSet(cursor)
            } catch (ex: SQLiteException) {
                //ex.printStackTrace();
                HandledErrorTracker.registerThrowable(ex)
            } catch (ex: Exception) {
                //ex.printStackTrace();
                HandledErrorTracker.registerThrowable(ex)
            } finally {
                cursor?.close()
                db?.close()
            }
        }
        return SelectCommand
    }


}