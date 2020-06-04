package com.sibi.pomodoro.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sibi.pomodoro.roomdb.dao.TaskDAO
import com.sibi.pomodoro.roomdb.entities.History
import com.sibi.pomodoro.roomdb.entities.Task

@Database(entities = [Task::class, History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): TaskDAO

    companion object{
        var INSTANCE:AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase?{
            if(INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"taskDB").build()
                }
            }
            return INSTANCE
        }

        fun destroy(context: Context){
            INSTANCE = null
        }
    }
}