package com.sibi.pomodoro.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.sibi.pomodoro.R
import com.sibi.pomodoro.recyclerviewadapter.CompleteAdapter
import com.sibi.pomodoro.roomdb.AppDatabase
import com.sibi.pomodoro.roomdb.entities.Task
import kotlinx.android.synthetic.main.fragment_completed_tasks.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class CompletedTasks : Fragment() {
    var db: AppDatabase? = null
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_completed_tasks, container, false)

        recyclerView = v.findViewById<RecyclerView>(R.id.completed_task_list)
        db = AppDatabase.getAppDatabase(context!!)
        readData()
        return v
    }

    fun readData() {
        CoroutineScope(IO).launch {
            val data = db?.dao()?.getCompletedTasks()!!
            applyData(data)
        }
    }

    fun applyData(data: List<Task>){
        CoroutineScope(Main).launch {
            val completeAdapter = CompleteAdapter(data)
            recyclerView?.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = completeAdapter
            }
        }
    }
}
