package com.sibi.pomodoro.roomdb.entities

import android.icu.util.DateInterval
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Task(
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "is_completed") var is_completed: Boolean
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}