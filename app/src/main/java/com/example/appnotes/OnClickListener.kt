package com.example.appnotes

interface OnClickListener {
    fun onChecked(note: Note) //evento del click normal
    fun onLongClick(note: Note)

}