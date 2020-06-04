package com.sibi.pomodoro.recyclerviewadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sibi.pomodoro.R
import com.sibi.pomodoro.roomdb.AppDatabase
import com.sibi.pomodoro.roomdb.entities.Task
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskAdapter() : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    var data:List<Task>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.current_task, parent, false)

        return TaskViewHolder(v)
    }

    override fun getItemCount(): Int {
        if(data != null){
            return data!!.size
        }else{
            return 0
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        if(data != null){
            holder.content?.text = data!![position].content.toString()
        }
    }

    fun addData(data: List<Task>){
        this.data = data
        this.notifyDataSetChanged()
    }

    //My viewholder
    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var content: TextView? = null
        var completedBtn: Button? = null

        init {
            content = view.findViewById(R.id.content)
            completedBtn = view.findViewById(R.id.mark_complete)
            (completedBtn as Button).setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            println("clicked item btn")
        }

    }
}