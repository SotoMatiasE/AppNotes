package com.example.appnotes

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.appnotes.databinding.ItemNoteBinding

class NoteAdapter (private var noteList: MutableList<Note>, private val listener: OnClickListener) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    private lateinit var context: Context //extreaemos contexto para poder acceder a los recursos


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        //inflamos la view y la mandamos al return
        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)

        return ViewHolder(view)
    }


    /*    ESTO ES UNA CLASE INTERNA QUE VA A PERMITIR INFLAR LA VISTA, ES DECIR QUE PODEMOS TOMAR
    ITEMNOTE Y CREARLA DENTRO DE RECYCLERVIEW LA PROPIEDAD DE activity_main <androidx.recyclerview.widget.RecyclerView*/
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemNoteBinding.bind(view) //llamamos a item_note.xml

        fun setListener(note: Note) { //implementar Class Note, recibe Note respecto a la que este seleccionada
            binding.cbFinished.setOnClickListener {
                note.isFinished = (it as CheckBox).isChecked
                listener.onChecked(note) //cambiamos el estado del check en pendientes
            }

            binding.root.setOnLongClickListener {
                listener.onLongClick(note, this@NoteAdapter)
                true
            }
        }
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

        //PREGUNTA SI LA NOTE ESTA FINALIZADA Y MODIFICA EN TIEMPO REAL
        if (note.isFinished){ //si esta finalizada cambia el tamaño del texto
            holder.binding.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                context.resources.getInteger(R.integer.description_finished_size).toFloat())
        }else {
            holder.binding.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                context.resources.getInteger(R.integer.description_default_size).toFloat())
        }
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