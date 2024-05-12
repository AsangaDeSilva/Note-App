package com.example.myapplication111

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        btnSave.setOnClickListener{
            if (editNoteTitle.text.isEmpty()){
                Toast.makeText(this, "Enter Note Title", Toast.LENGTH_SHORT).show()
                editNoteTitle.requestFocus()
            } else
            {
                val note = Note()
                note.noteTitle = editNoteTitle.text.toString()
                if (editNoteDescription.text.isEmpty())
                    note.noteDescription = editNoteDescription.text.toString()
                MainActivity.dbHandler.addNote(this, note)
                clearEdits()
                editNoteTitle.requestFocus
            }
        }

        btnCancel.setOnClickListener{
            clearEdits()
            finish()
        }
    }
    private fun clearEdits(){
        editNoteTitle.text.clear()
        editNoteDescription.text.clear()
    }
}