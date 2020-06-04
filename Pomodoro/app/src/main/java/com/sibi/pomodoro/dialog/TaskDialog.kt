package com.sibi.pomodoro.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.sibi.pomodoro.R
import java.lang.ClassCastException
import java.lang.Exception

class TaskDialog : DialogFragment() {
    internal lateinit var listener: TaskDialogListener

    interface TaskDialogListener {
        fun taskPositiveClickListener(dialog: DialogFragment, editText: EditText)
        fun taskNegativeClickListener(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = targetFragment as TaskDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString()+"Activity did not implement the interface")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view: View = inflater.inflate(R.layout.add_task_dialog, null)

            var editText = view.findViewById<EditText>(R.id.input_task)
            builder.setView(view)
            builder.setPositiveButton(
                R.string.add,
                DialogInterface.OnClickListener { dialog, which -> listener.taskPositiveClickListener(this, editText)})
            builder.setNegativeButton(
                R.string.cancel,
                DialogInterface.OnClickListener { dialog, which -> listener.taskNegativeClickListener(this)})
            builder.create()

        } ?: throw Exception("Activity is null")
    }

}