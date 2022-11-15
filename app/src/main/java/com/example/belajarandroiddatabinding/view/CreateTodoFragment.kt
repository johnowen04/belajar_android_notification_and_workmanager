package com.example.belajarandroiddatabinding.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.belajarandroiddatabinding.databinding.FragmentCreateTodoBinding
import com.example.belajarandroiddatabinding.model.Todo
import com.example.belajarandroiddatabinding.util.TodoWorker
import com.example.belajarandroiddatabinding.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.view.*
import java.util.*
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment(),
    TodoAddClickListener,
    TodoSetDueListener,
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var dataBinding: FragmentCreateTodoBinding

    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        dataBinding.todo = Todo("", "", 3)
        dataBinding.addListener = this
        dataBinding.listener = object: RadioButtonListener {
            override fun onRadioClick(view: View, priority: Int, todo: Todo) {
                todo.priority = priority
            }
        }
        dataBinding.dueListener = this

        /*
        btnAddNotes.setOnClickListener {
            val radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)

            val todo = Todo(
                editTitle.text.toString(),
                editNotes.text.toString(),
                radio.tag.toString().toInt()
            )

            val list = listOf(todo)

            viewModel.addTodo(list)
            Toast.makeText(view.context, "Data added", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()

            /*
            NotificationHelper(view.context)
                .createNotification(
                    "Todo Created",
                    "A new todo has been created. Stay focused!"
                )
             */

            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                        "TITLE" to "Todo Created",
                        "CONTENT" to "A new todo has been created. Stay focused!"
                    )
                )
                .build()

            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
        }
        */
    }

    override fun onAddClickListener(view: View) {
        val c = Calendar.getInstance()
        c.set(year, month, day, hour, minute, 0)
        val today = Calendar.getInstance()
        val diff = (c.timeInMillis/1000L) - (today.timeInMillis/1000L)

        dataBinding.todo?.let {
            it.todoDate = (c.timeInMillis/1000L).toInt()

            val list = listOf(it)
            viewModel.addTodo(list)

            Toast.makeText(view.context, "Data added", Toast.LENGTH_SHORT).show()
            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(diff, TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                        "TITLE" to "Todo Created",
                        "CONTENT" to "A new todo has been created. Stay focused!"
                    )
                )
                .build()

            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
            Navigation.findNavController(view).popBackStack()
        }
    }

    override fun onDateClick(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        activity?.let {
            DatePickerDialog(it, this, year, month, day).show()
        }
    }

    override fun onTimeClick(view: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        TimePickerDialog(
            activity,
            this,
            hour,
            minute,
            DateFormat.is24HourFormat(activity)
        ).show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Calendar.getInstance().let {
            it.set(year, month, day)
            dataBinding.root.editDate.setText("""
                ${day.toString().padStart(2, '0')} - ${month.toString().padStart(2, '0')} - ${year.toString()}""")

            this.year = year
            this.month = month
            this.day = day
        }
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        dataBinding.root.editTime.setText("""
                ${hourOfDay.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}""")

        this.hour = hourOfDay
        this.minute = minute
    }
}