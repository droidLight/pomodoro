package com.sibi.pomodoro.roomdb.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "task_description") var taskDesc: String
)