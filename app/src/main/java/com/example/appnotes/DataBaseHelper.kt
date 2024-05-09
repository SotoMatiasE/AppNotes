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
                          "${Constants.PROPERTY_DESCRIPTION} VARCHAR(60), ${Constants.PROPERTY_IS_FINISHED} BOOLEAN)" //creamos la db con OBJ Note
        db?.execSQL(createTable) //le decimo que se ejecute
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            //ESTE METODO ES PARA ALTERAR LA DB
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
}
















