package com.example.us06_2

import Persistence.OpenHelper
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var dbHelper: OpenHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getAllProjects()

    }

    private fun getAllProjects(){
        dbHelper = OpenHelper(this)
        val listaP = dbHelper.getProjects()
        val recycler = findViewById<RecyclerView>(R.id.rvProjects)
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        recycler.adapter = Adapter(listaP)
    }
}