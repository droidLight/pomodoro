package com.sibi.pomodoro.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.sibi.pomodoro.R
import com.sibi.pomodoro.TimerActivity
import com.sibi.pomodoro.dialog.TaskDialog
import com.sibi.pomodoro.recyclerviewadapter.TaskAdapter
import com.sibi.pomodoro.roomdb.AppDatabase
import com.sibi.pomodoro.roomdb.entities.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class TodoFragment : Fragment(), View.OnClickListener, TaskDialog.TaskDialogListener{

    var taskAdapter: TaskAdapter? = null
    var dialogFrag: TaskDialog? = null
    var db: AppDatabase? = null

    val TODO_FRAG_REQ_CODE: Int = 200

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_todo, container, false)

        //datbase object
        db = AppDatabase.getAppDatabase(container?.context!!)

        //views
        val addTaskBtn = view.findViewById<Button>(R.id.add_btn)
        addTaskBtn.setOnClickListener(this)
        val startTimer = view.findViewById<Button>(R.id.timer_btn)
        startTimer.setOnClickListener(this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.task_recycler_view)

        //task dialog fragment
        dialogFrag = TaskDialog()
        dialogFrag?.setTargetFragment(this, TODO_FRAG_REQ_CODE)

        //recycler adapter, callback implmented using lambda function
        taskAdapter = TaskAdapter{item -> completedBtnClicked(item)}

        //recycler view applying adapter and layout manager
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = taskAdapter
        }

        //display data in the recycler view
        readData()

        return view
    }

    //callback listener from completed button in each task implemented using lambda function
    fun completedBtnClicked(taskId: Int){
        println("TASK ID: $taskId")
        CoroutineScope(IO).launch {
            db!!.dao().markComplete(taskId)
        }
        //to update list
        readData()
    }

    //listener for add btn and timer btn
    override fun onClick(v: View?) {
        if (v?.id == R.id.add_btn) {
            activity?.supportFragmentManager?.let { dialogFrag?.show(it, "task-dialog") }
        }
        if(v?.id == R.id.timer_btn){
            activity?.startActivity(Intent(this.activity, TimerActivity::class.java))

        }
    }

    //listener for positive btn in task dialog
    override fun taskPositiveClickListener(dialog: DialogFragment, editText: EditText) {
        CoroutineScope(IO).launch {
            //saving the data into the database
            db!!.dao().addTodo(Task(content = editText.text.toString(), is_completed = false))
            readData()
        }
        dialog.dismiss()
    }

    fun readData() {
        CoroutineScope(IO).launch {
            //reading the database for list of tasks
            val data = db!!.dao().getUncompletedTasks()
            updateRecyclerView(data)
        }
    }

    suspend fun updateRecyclerView(data:List<Task>){
        CoroutineScope(Main).launch {
            taskAdapter?.addData(data)
        }
    }

    //listener for negative btn in task dialog
    override fun taskNegativeClickListener(dialog: DialogFragment) {
        dialog.dismiss()
    }

}
