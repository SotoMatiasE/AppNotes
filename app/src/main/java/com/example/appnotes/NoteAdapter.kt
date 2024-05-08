package com.example.appnotes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appnotes.databinding.ItemNoteBinding

class NoteAdapter {

/*    ESTO ES UNA CLASE INTERNA QUE VA A PERMITIR INFLAR LA VISTA, ES DECIR QUE PODEMOS TOMAR
    ITEMNOTE Y CREARLA DENTRO DE RECYCLERVIEW LA PROPIEDAD DE activity_main <androidx.recyclerview.widget.RecyclerView*/
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemNoteBinding.bind(view)
    }
}