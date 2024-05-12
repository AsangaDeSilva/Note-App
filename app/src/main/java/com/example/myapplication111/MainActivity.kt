package com.example.myapplication111

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var dbHandler: DBHandler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = DBHandler(this, null, null, 1)

        viewNotes()

        fab.setOnClickListener{
            val i = Intent(this, AddNoteActivity::class.java)
            startActivity(i)
        }
    }

    private fun viewNotes(){
        val notesList = dbHandler.getNotes(this)
        val adopter = NoteAdopter(this, notesList)
        val rv : RecyclerView = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager
        rv.adapter = adopter
    }

    override fun onResume(){
        viewNotes()
        super.onResume()
    }
}