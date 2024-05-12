package com.example.myapplication111

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast

class NoteAdopter(mCtx:Context, val notes : ArrayList<Note>) : RecyclerView.Adapter<NoteAdopter.ViewHolder>(){

    val mCtx = mCtx

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtNoteTitle = itemView.findViewById<TextView>(R.id.txtNoteTitle)
        val txtNoteDescription = itemView.findViewById<TextView>(R.id.txtNoteDescription)
        val btnUpdate = itemView.findViewById<TextView>(R.id.btnupdate)
        val btnDelete = itemView.findViewById<TextView>(R.id.btndelete)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NoteAdopter.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.lo_notes,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(p0: NoteAdopter.ViewHolder, p1: Int) {
        val note : Note = notes[p1]
        p0.txtNoteTitle.text = note.noteTitle
        p0.txtNoteDescription.text = note.noteDescription

        p0.btnDelete.setOnClickListener {
            val noteTitle = note.noteTitle
            val alertDialog = AlertDialog.Builder(mCtx)
                .setTitle("Warning")
                .setMessage("Are you sure to delete : $noteTitle ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener{dialog, which ->
                    if (MainActivity.dbHandler.deleteNote(note.noteID)){
                        notes.removeAt(p1)
                        notifyItemRemoved(p1)
                        notifyItemRangeChanged(p1, notes.size)
                        Toast.makeText(mCtx, "Note $noteTitle Deleted", Toast.LENGTH_SHORT).show()
                    }else
                        Toast.makeText(mCtx, "Error deleting", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener{dialog, which ->  })
                .setIcon(R.drawable.baseline_details_24)
                .show()
        }
        p0.btnUpdate.setOnClickListener {
            val inflater = LayoutInflater.from(mCtx)
            val view = inflater.inflate(R.layout.lo_note_update, null)

            val txtNoteTitle : TextView = view.findViewById(R.id.editUpNoteTitle)
            val txtNoteDescription : TextView = view.findViewById(R.id.editUpNoteDescription)

            txtNoteTitle.text = note.noteTitle
            txtNoteDescription.text = note.noteDescription

            val builder = AlertDialog.Builder(mCtx)
                .setTitle("Update Note info.")
                .setView(view)
                .setPositiveButton("Update", DialogInterface.OnClickListener{dialog, which ->
                    val isUpdate = MainActivity.dbHandler.updateNote(
                        note.noteID.toString(),
                        view.editUpNoteTitle.text.toString(),
                        view.editUpNoteDescription.text.toString())
                    if (isUpdate==true){
                        notes[p1].noteTitle = view.editUpNoteTitle.text.toString()
                        notes[p1].noteDescription = view.editUpNoteDescription.text.toString()
                        notifyDataSetChanged()
                        Toast.makeText(mCtx, "Updated Successful", Toast.LENGTH_SHORT).show()
                    }else
                        Toast.makeText(mCtx, "Error updating", Toast.LENGTH_SHORT).show()
                }).setNegativeButton("Cancel", DialogInterface.OnClickListener{dialog, which ->

                })
            val alert = builder.create()
            alert.show()
        }
    }

}