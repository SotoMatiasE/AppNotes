package com.example.appnotes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


// DEFINIR DB Y ALGUNOS METODOS Ctrl + i trae los parametros de SQLiteOpenHelper
class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, Constants.DATABASE_NAME,
                                                    null, Constants.DATABASE_VERSION)
{
    override fun onCreate(db: SQLiteDatabase?) {
            //DEFINIR LA ESTRUCTURA DE LA DB Y DEFINIR LAS TABLAS QUE ESTAN RELACIUONANDO E INTERACTUANDO
        val createTable = "CREATE TABLE ${Constants.ENTITY_NOTE} (" +
                          "${Constants.PROPERTY_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                          "${Constants.PROPERTY_DESCRIPTION} STRING(60), ${Constants.PROPERTY_IS_FINISHED} BOOLEAN)" //creamos la db con OBJ Note
        db?.execSQL(createTable) //le decimo que se ejecute
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            //ESTE METODO ES PARA ALTERAR LA DB
    }

    fun getAllNotes(): MutableList<Note>{
        val notes: MutableList<Note> = mutableListOf()

        val database = this.readableDatabase
        val query = "SELECT * FROM ${Constants.ENTITY_NOTE}"

        val result = database.rawQuery(query, null)

        if (result.moveToFirst()){
            do {
                val id = result.getColumnIndex(Constants.PROPERTY_ID)
                val description = result.getColumnIndex(Constants.PROPERTY_DESCRIPTION)
                val isFinished = result.getColumnIndex(Constants.PROPERTY_IS_FINISHED)
                val note = Note()
                note.id = result.getLong(if (id >= 0) id else 0)
                note.description = result.getString(if (description >= 0) description else 0)
                note.isFinished = result.getInt( if (isFinished >= 0) isFinished else 0) == Constants.TRUE

                notes.add(note)
            } while (result.moveToNext())
        }
        result.close() //se agrega para que database.rawQuery(query, null) no marque eror
        return notes
    }


    fun insertNote(note: Note): Long{
        val database = this.writableDatabase //readableDatabase abre la escritura de la DB
        val contentValues = ContentValues().apply {
            put(Constants.PROPERTY_DESCRIPTION, note.description)
            put(Constants.PROPERTY_IS_FINISHED, note.isFinished)
        }
        //insertar en la tabla almacenado en la variable result
        val resultId = database.insert(Constants.ENTITY_NOTE, null, contentValues)

        return resultId
    }

    fun  updateNote(note: Note): Boolean{
        val database = this.writableDatabase //readableDatabase abre la escritura de la DB
        val contentValues = ContentValues().apply {
            put(Constants.PROPERTY_DESCRIPTION, note.description)
            put(Constants.PROPERTY_IS_FINISHED, note.isFinished)
        }

        val result = database.update(
            Constants.ENTITY_NOTE,
            contentValues,
            "${Constants.PROPERTY_ID} = ${note.id}", // "id = 1"
            null)

        return result == Constants.TRUE
    }
    fun deleteNote(note: Note) : Boolean{
        val database = this.writableDatabase
        val result = database.delete(Constants.ENTITY_NOTE, "${Constants.PROPERTY_ID}, " +
                                    "${note.id}", null)


        return result == Constants.TRUE
    }
}
















