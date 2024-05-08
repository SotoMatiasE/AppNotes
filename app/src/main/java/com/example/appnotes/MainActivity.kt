package com.example.appnotes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
            }
        }
    }

    private fun addNoteAutom(note: Note){
        noteAdaptrer.add(note)
    }
    override fun onLongClick(note: Note) {

    }
}