package com.example.appnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appnotes.databinding.ItemNoteBinding

class NoteAdapter (private var noteList: MutableList<Note>, private val listener: OnClickListener) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
/*    ESTO ES UNA CLASE INTERNA QUE VA A PERMITIR INFLAR LA VISTA, ES DECIR QUE PODEMOS TOMAR
    ITEMNOTE Y CREARLA DENTRO DE RECYCLERVIEW LA PROPIEDAD DE activity_main <androidx.recyclerview.widget.RecyclerView*/
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemNoteBinding.bind(view) //llamamos a item_note.xml

        fun setListener(note: Note) { //implementar Class Note, recibe Note respecto a la que este seleccionada
            binding.root.setOnLongClickListener {
                listener.onLongClick(note)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflamos la view y la mandamos al return
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = noteList.size  //VA A INDICAR CUANTOS ELEMENTOS QUEREMOS VER EN EL LISTADO


    //onBindViewHolder es para modificar la parte de la vista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]

        //CONFIGURAMOS EL LISTENER DE inner class ViewHolder
        holder.setListener(note)

        //accede a la vista activity_main id tvDecription
        holder.binding.tvDescription.text = note.description

        //si tiene el valor cel check en true se auto selecciona
        holder.binding.cbFinished.isChecked = note.isFinished
    }

    fun add(note: Note) {
        noteList.add(note)
        notifyDataSetChanged() //actualiza
    }
    fun remove(note: Note) {
        noteList.remove(note)
        notifyDataSetChanged() //actualiza
    }
}