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

        binding.btnAdd.setOnClickListener { //AGREGAMOS EL EVENTO DE CLICK AL BOTON
            if (binding.etDescription.text.toString().isNotBlank()) {
                val note = Note(description = binding.etDescription.text.toString().trim())
                note.id = database.insertNote(note)

                if (note.id != -1L){
                    addNoteAutom(note)
                    binding.etDescription.text?.clear()
                    Snackbar.make(binding.root, "Operacion exitosa.", Snackbar.LENGTH_SHORT).show()
                }else {
                    Snackbar.make(binding.root, "Error al modificar la Base de Datos.", Snackbar.LENGTH_SHORT).show()
                }
            }else {
                binding.etDescription.error = getString(R.string.validation_field_require)
            }
        }
    }

    override  fun onStart(){
        super.onStart()
        getData()
    }


    private fun getData(){
        val data = mutableListOf(Note(1, "Estudiar kotlin"),
                                 Note(2, "Jugar fulbito"))

        data.forEach { note -> //por cada nota agregalo al adaptador
            addNoteAutom(note)
        }
    }

    private fun addNoteAutom(note: Note){
        if (note.isFinished){
            notesFinishAdapter.add(note)
        }else {
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
        //eliminar la nota de donde se encuentra actualmente y moverla al siguente rcView
        deleteNoteAutom(note)
        addNoteAutom(note)
    }

    override fun onLongClick(note: Note, currentAdapter: NoteAdapter) {
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setPositiveButton(getString(R.string.dialog_ok)) { dialogInterface, i ->
                currentAdapter.remove(note)
            }
            .setNegativeButton(getString(R.string.dialog_cancel), null)

        builder.create().show()
    }

}