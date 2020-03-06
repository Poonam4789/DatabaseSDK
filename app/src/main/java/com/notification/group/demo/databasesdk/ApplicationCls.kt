package com.notification.group.demo.databasesdk

import android.app.Application
import com.notification.group.demo.kdatabasesdk.DatabaseManager

class ApplicationCls : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseManager.initialize(this);
    }
}