package com.example.appnotes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdaptrer: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = mutableListOf(Note(1, "Estudiar kotlin"),
                                 Note(2, "Jugar fulbito"))

        noteAdaptrer = NoteAdapter(data, this)
        binding.recyclerView.apply {//vinculacion con recycler_view de activity_main
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdaptrer
        }
        binding.btnAdd.setOnClickListener { //AGREGAMOS EL EVENTO DE CLICK AL BOTON
            if (binding.etDescription.text.toString().isNotBlank()) {
                val note = Note((noteAdaptrer.itemCount +1).toLong(),
                    binding.etDescription.text.toString().trim())
                addNoteAutom(note)
                binding.etDescription.text?.clear()
            }else {
                binding.etDescription.error = getString(R.string.validation_field_require)
            }
        }
    }

    private fun addNoteAutom(note: Note){
        noteAdaptrer.add(note)
    }
    private fun deleteNoteAutom(note: Note){
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setPositiveButton(getString(R.string.dialog_ok)) { dialogInterface, i ->
                noteAdaptrer.remove(note)
            }
            .setNegativeButton(getString(R.string.dialog_cancel), null)

        builder.create().show()
    }

    override fun onChecked(note: Note) {

    }

    override fun onLongClick(note: Note) {
        deleteNoteAutom(note)
    }

}