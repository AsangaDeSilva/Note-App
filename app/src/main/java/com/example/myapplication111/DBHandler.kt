package com.example.myapplication111

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf

class DBHandler (context: Context, name:String?, factory:SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

        companion object{
            private val DATABASE_NAME = "MyData.db"
            private val DATABASE_VERSION = 1

            val NOTES_TABLE_NAME = "Notes"
            val COLUMN_NOTEID = "noteid"
            val COLUMN_NOTETITLE = "notetitle"
            val COLUMN_NOTEDESCRIPTION = "notedescription"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_NOTES_TABLE = ("CREATE TABLE $NOTES_TABLE_NAME (" +
                "$COLUMN_NOTEID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NOTETITLE TEXT," +
                "$COLUMN_NOTEDESCRIPTION TEXT")
        db?.execSQL(CREATE_NOTES_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun getNotes(mCtx : Context) : ArrayList<Note>{
        val qry = "Select * From $NOTES_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val notes = ArrayList<Note>()

        if (cursor.count == 0)
            Toast.makeText(mCtx, "No Records Found", Toast.LENGTH_SHORT).show() else {
                cursor.moveToFirst()
            while (!cursor.isAfterLast()){
            val note = Note()
            note.noteID = cursor.getInt(with(cursor) { getColumnIndex(COLUMN_NOTEID) })
            note.noteTitle = cursor.getString(with(cursor) { getColumnIndex(COLUMN_NOTETITLE) })
            note.noteDescription = cursor.getString(with(cursor) { getColumnIndex(COLUMN_NOTEDESCRIPTION) })
            notes.add(note)
        }

            Toast.makeText(mCtx, "${cursor.count.toString()} Records Found", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return notes

    }

    fun addNote(mCtx: Context, note: Note){
        val values = ContentValues()
        values.put(COLUMN_NOTETITLE, note.noteTitle)
        values.put(COLUMN_NOTEDESCRIPTION, note.noteDescription)
        val db = this.writableDatabase
        try {
            db.insert(NOTES_TABLE_NAME, null, values)
            Toast.makeText(mCtx, "Note Added", Toast.LENGTH_SHORT).show()
        } catch (e : Exception){
            Toast.makeText(mCtx, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun deleteNote(noteID : Int) : Boolean{
        val qry = "Delete From $NOTES_TABLE_NAME where $COLUMN_NOTEID = $noteID"
        val db = this.writableDatabase
        var result : Boolean = false
        try {
            val cursor = db.execSQL(qry)
            result = true
        }catch (e : Exception){
            Log.e(ContentValues.TAG, "Error deleting")
        }
        db.close()
        return result
    }

    fun updateNote(id : String, noteTitle : String, noteDescription : String) : Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        var result : Boolean = false
        contentValues.put(COLUMN_NOTETITLE, noteTitle)
        contentValues.put(COLUMN_NOTEDESCRIPTION, noteDescription)
        try {
            db.update(NOTES_TABLE_NAME, contentValues, "$COLUMN_NOTEID = ?", arrayOf(id))
            result = true
        } catch (e : Exception){
            Log.e(ContentValues.TAG, "Error updating")
            result = false
        }
        return result
    }
}
