package com.sibi.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sibi.pomodoro.fragments.CompletedTasks
import com.sibi.pomodoro.fragments.TodoFragment

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.container, TodoFragment(), "todo_frag").commit()
        }

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_bar)
        bottomNavBar.setOnNavigationItemSelectedListener(navSelectorListener)
    }

    private val navSelectorListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.todo_page -> {
                    val todoPage = TodoFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, todoPage, "todo_frag").commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.completed_page -> {
                    val completedPage = CompletedTasks()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, completedPage, "completed_frag").commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

}
