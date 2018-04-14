package com.i7play.pameizi.base

import android.app.Application
import com.i7play.pameizi.BuildConfig
import com.i7play.supertian.manager.ActivityManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import xiaofei.library.datastorage.database.DatabaseStorage

/**
 * Created by tian on 2018/4/14.
 */
class App : Application() {
    companion object {
        lateinit var Instance : App
    }

    lateinit var db: DatabaseStorage
    override fun onCreate() {
        super.onCreate()
        Instance = this
        ActivityManager.init(this)
        db = DatabaseStorage.getInstance(this)
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}