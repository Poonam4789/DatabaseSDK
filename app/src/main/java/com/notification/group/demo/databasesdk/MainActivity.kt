package com.notification.group.demo.databasesdk

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.notification.group.demo.kdatabasesdk.DatabaseManager
import com.notification.group.demo.kdatabasesdk.queries.DeleteCommand
import com.notification.group.demo.kdatabasesdk.queries.InsertCommand
import com.notification.group.demo.kdatabasesdk.queries.SelectCommand
import com.notification.group.demo.kdatabasesdk.queries.UpdateCommand

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addData()
        updateData()
        getData()
        deleteData()
    }

    fun addData() {
        for (i in 0..9) {
            val insertCommand = InsertCommand("employee")
            insertCommand.insert("emp_id", i + 1)
            insertCommand.insert("emp_name", "Employee " + (i + 1))
            val lastrow = DatabaseManager.executeQuery(insertCommand)
            Log.d("DB", "addData: $lastrow")
        }
    }

    private fun updateData() {
        for (i in 0..9) {
            val updateCommand = UpdateCommand("employee", "emp_id=" + (i + 1))
            updateCommand.update("emp_dept", "Test")
            val rowsaffeted = DatabaseManager.executeQuery(updateCommand)
            Log.d("DB", "updateData: $rowsaffeted")
        }
    }

    private fun getData() {
        val selectCommand = object : SelectCommand() {
            override fun onResultSet(cursor: Cursor) {
                if (cursor.moveToFirst()) {
                    do {
                        // Passing values
                        val column1 = cursor.getInt(0)
                        val column2 = cursor.getString(1)
                        val column3 = cursor.getString(2)
                        // Do something Here with values
                        Log.d("db", "getData: $column1 $column2 $column3")
                    } while (cursor.moveToNext())
                }
            }
        }
        DatabaseManager.fillData("select * from employee", selectCommand)
    }

    fun deleteData() {
        for (i in 0..9) {
            val deleteCommand = DeleteCommand("employee", "emp_id=" + (i + 1))
            val rowsaffeted = DatabaseManager.executeQuery(deleteCommand)
            Log.d("DB", "deleteData: $rowsaffeted")
        }
    }


}
