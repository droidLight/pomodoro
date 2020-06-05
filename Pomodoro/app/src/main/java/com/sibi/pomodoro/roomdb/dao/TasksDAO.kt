package com.sibi.pomodoro.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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

    @Query("update todo set is_completed = 1 where id = :id")
    fun markComplete(vararg id: Int)

}