package com.sibi.pomodoro.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sibi.pomodoro.roomdb.entities.History
import com.sibi.pomodoro.roomdb.entities.Task

@Dao
interface TaskDAO {
    //for completed and current tasks
    @Query("select * from todo where is_completed = 1")
    fun getCompletedTasks(): List<Task>

    @Query("select * from todo where is_completed = 0")
    fun getUncompletedTasks(): List<Task>

    @Insert
    fun addTodo(vararg todo: Task)

    @Update
    fun markComplete(vararg todo: Task)

    //for history of tasks user saved
    @Query("select * from history")
    fun getHistory(): List<History>

    @Insert
    fun addHistory(vararg history: History)
}