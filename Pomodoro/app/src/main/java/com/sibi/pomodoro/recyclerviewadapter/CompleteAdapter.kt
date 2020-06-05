package com.sibi.pomodoro.recyclerviewadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sibi.pomodoro.R
import com.sibi.pomodoro.roomdb.entities.Task

class CompleteAdapter(val data: List<Task>):RecyclerView.Adapter<CompleteAdapter.CompleteViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompleteViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.completed_task_item, parent, false)
        return CompleteViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CompleteViewHolder, position: Int) {
        val temp = data[position]
        holder.content?.text = data[position].content
    }

    //view holder
    class CompleteViewHolder(view: View): RecyclerView.ViewHolder(view){
        var content: TextView? = null
        init {
            content = view.findViewById(R.id.completed_task_content)
        }
    }
}