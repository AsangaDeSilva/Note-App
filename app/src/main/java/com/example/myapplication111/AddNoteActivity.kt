package com.example.myapplication111

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        findViewById<TextView>(R.id.btnSave).setOnClickListener{
            if (findViewById<TextView>(R.id.editNoteTitle).text.isEmpty()){
                Toast.makeText(this, "Enter Note Title", Toast.LENGTH_SHORT).show()
                findViewById<TextView>(R.id.editNoteTitle).requestFocus()
            } else
            {
                val note = Note()
                note.noteTitle = findViewById<TextView>(R.id.editNoteTitle).text.toString()
                if (findViewById<TextView>(R.id.editNoteDescription).text.isEmpty())
                    note.noteDescription = findViewById<TextView>(R.id.editNoteDescription).text.toString()
                MainActivity.dbHandler.addNote(this, note)
                clearEdits()
                findViewById<TextView>(R.id.editNoteTitle).requestFocus()
            }
        }

        findViewById<TextView>(R.id.btnCancel).setOnClickListener{
            clearEdits()
            finish()
        }
    }
    private fun clearEdits(){
        findViewById<EditText>(R.id.editNoteTitle).text.clear()
        findViewById<EditText>(R.id.editNoteDescription).text.clear()
    }
}