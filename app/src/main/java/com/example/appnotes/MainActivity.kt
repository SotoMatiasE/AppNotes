package com.example.appnotes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnotes.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdaptrer: NoteAdapter
    private lateinit var notesFinishAdapter: NoteAdapter

    //VARIABLE PARA INSTANCIAMOS LA CLASE DataBaseHelper
    private lateinit var database: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = DataBaseHelper(this) //INSTANCIAMOS LA CLASE DataBaseHelper

        noteAdaptrer = NoteAdapter(mutableListOf(), this)
        binding.rvNotes.apply {//vinculacion con recycler_view de activity_main
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdaptrer
        }

        notesFinishAdapter = NoteAdapter(mutableListOf(), this)
        binding.rvNotesFinished.apply {//vinculacion con recycler_view de activity_main
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notesFinishAdapter
        }

        binding.btnAdd.setOnClickListener {
            if (binding.etDescription.text.toString().isNotBlank()){
                val note = Note(description = binding.etDescription.text.toString().trim())
                note.id = database.insertNote(note)

                if (note.id != Constants.ID_ERROR.toLong()) {
                    addNoteAutom(note)
                    binding.etDescription.text?.clear()
                    showMessage(R.string.message_write_db_succes)
                } else {
                    showMessage(R.string.message_db_error)
                }
            } else {
                binding.etDescription.error = getString(R.string.validation_field_require)
            }
        }
    }

    override  fun onStart(){
        super.onStart()
        getData()
    }


    private fun getData(){
        /*val data = mutableListOf(Note(1, "Estudiar kotlin"),
                                 Note(2, "Jugar fulbito"))*/


        val data = database.getAllNotes()
        data.forEach { note -> //por cada nota agregalo al adaptador
            addNoteAutom(note)
        }
    }

    private fun addNoteAutom(note: Note) {
        if (note.isFinished){
            notesFinishAdapter.add(note)
        } else {
            noteAdaptrer.add(note)
        }
    }
    private fun deleteNoteAutom(note: Note){
        if (note.isFinished) {
            noteAdaptrer.remove(note)
        }else {
            notesFinishAdapter.remove(note)
        }
    }

    override fun onChecked(note: Note) {
        if (database.updateNote(note)) {
            //eliminar la nota de donde se encuentra actualmente y moverla al siguente rcView
            deleteNoteAutom(note)
            addNoteAutom(note)
        }else {
            showMessage(R.string.message_db_error)
        }
    }

    override fun onLongClick(note: Note, currentAdapter: NoteAdapter) {
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setPositiveButton(getString(R.string.dialog_ok)) { _, _ ->
                if (database.deleteNote(note)){
                    currentAdapter.remove(note)
                    showMessage(R.string.message_write_db_succes)
                }else {
                    showMessage(R.string.message_db_error)
                }
            }
            .setNegativeButton(getString(R.string.dialog_cancel), null)

        builder.create().show()
    }

    private fun showMessage(msgRes: Int){
        Snackbar.make(binding.root,getString(msgRes), Snackbar.LENGTH_SHORT).show()

    }

}