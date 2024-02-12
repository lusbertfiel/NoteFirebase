package com.fiel.notefirebase.ui.data

import com.fiel.notefirebase.ui.domain.model.DataResponse
import com.fiel.notefirebase.ui.domain.model.Note
import com.fiel.notefirebase.ui.domain.repository.Repository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val refNotes:CollectionReference):Repository{

    override suspend fun insertNote(note: Note): DataResponse<Boolean> {
        return try {
            val id=refNotes.document().id
            note.id=id
            refNotes.document(id).set(note).await()
            DataResponse.Success(true)
        }catch (e:Exception){
            DataResponse.Failure(e)
        }
    }

    override suspend fun deleteNote(note: Note): DataResponse<Boolean> {
        return try {
            refNotes.document(note.id).delete().await()
            DataResponse.Success(true)
        }catch (e:Exception){
            DataResponse.Failure(e)
        }
    }

    override suspend fun updateNote(note: Note): DataResponse<Boolean> {
        return try {
            val map :MutableMap<String,Any> = HashMap()
            map["titulo"]=note.titulo
            map["contenido"]=note.contenido
            refNotes.document(note.id).update(map).await()
            DataResponse.Success(true)
        }catch (e:Exception){
            DataResponse.Failure(e)
        }
    }

    override fun getNotes(): Flow<DataResponse<List<Note>>> = callbackFlow {
        val snaplistener=refNotes.addSnapshotListener { value, error ->
            GlobalScope.launch(Dispatchers.IO) {
                val respuesta=if (value != null){
                    val notes=value.toObjects(Note::class.java)
                    DataResponse.Success(notes)
                }else{
                    DataResponse.Failure(error)
                }
                trySend(respuesta)
            }
        }
        awaitClose { snaplistener.remove() }
    }

}