package com.notification.group.demo.kdatabasesdk

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

class QueryBuilder(context: Context) {

    private val _context: Context = context
    private var _dbname: String? = null
    private var _dbversion: Int = 1
    private val _queriesWithUniqueID: HashMap<String, QueryInfo> = HashMap<String, QueryInfo>()
    private val _ddlQuries: ArrayList<QueryInfo> = ArrayList<QueryInfo>()
    private val _ddlChangesQueries: ArrayList<QueryInfo> = ArrayList<QueryInfo>()


    init {
        loadQueries()
    }
    fun loadQueries() {
        try {
            var json: JSONObject? = convertToJson(getFileContent("dbschema.json")!!)
            _dbname = json?.getString("NAME")
            _dbversion = json?.getInt("VERSION")!!
            val arr = json.getJSONArray("QUERIES")
            for (i in 0..arr.length()) {
                var obj: JSONObject = arr.getJSONObject(i)
                var q = QueryInfo(obj.getString("ID"), obj.getInt("TYPE"), obj.getString("TEXT"))
                _queriesWithUniqueID.put(q.id, q)
                if (q.type == 1) {
                    //All queries to create table by sdk will give type 1
                    _ddlQuries.add(q)
                } else if (q.type == 11) {
                    //All queries to alter table will give type 11

                    try {
                        val qver = obj.getInt("VERSION")
                        q.version = qver
                    } catch (ex: Exception) {

                    }

                    _ddlChangesQueries.add(q)
                } else if (q.type == 10) {
                    //All queries to create table by apps will give type 10
                    try {
                        val qver = obj.getInt("VERSION")
                        q.version = (qver)
                    } catch (ex: Exception) {

                    }

                    _ddlChangesQueries.add(q)
                    _ddlQuries.add(q)
                }
            }
        } catch (e: JSONException) {
            HandledErrorTracker.registerThrowable(e)
        } catch (e: Exception) {
            HandledErrorTracker.registerThrowable(e)
        }
    }

    fun getDBName(): String? {
        if (_dbname == null) {
            _dbname = "data-db.sqlite"
        }
        return _dbname
    }

    fun getDBVersion(): Int {
        return _dbversion
    }

    fun getDDLQueries(): ArrayList<String> {
        var str = ArrayList<String>()
        for (queryInfo in _ddlQuries) {
            str.add(queryInfo.text)
        }
        return str
    }

    fun getDDLChangesQueries(oldversion: Int): ArrayList<String> {
        val str = ArrayList<String>()
        for (queryInfo in _ddlChangesQueries) {
            if (queryInfo.version > oldversion) {
                str.add(queryInfo.text)
            }
        }
        return str
    }

    fun getFileContent(fileName: String): String? {
        try {
            val _is: InputStream = _context.assets.open(fileName)
            val _size: Int = _is.available()
            val _buffer = ByteArray(_size) { 0 }
            _is.read(_buffer)
            _is.close()
            return String(_buffer)
        } catch (e: IOException) {
            HandledErrorTracker.registerThrowable(e)
        }
        return null
    }

    fun convertToJson(str: String): JSONObject? {
        try {
            val jsonObject = JSONObject(str)
            return jsonObject
        } catch (t: Throwable) {
            HandledErrorTracker.registerThrowable(t)
        }
        return null
    }
}