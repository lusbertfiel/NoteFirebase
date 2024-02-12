package com.fiel.notefirebase.ui.domain.model

import com.google.gson.Gson

data class Note(var id:String="",var titulo:String="",var contenido:String=""){

    fun toJson():String=Gson().toJson(
        Note(
            id=id,
            titulo = titulo,
            contenido = contenido
        )
    )
    companion object{
        fun fromJson(data:String):Note=Gson().fromJson(data,Note::class.java)
    }

}
