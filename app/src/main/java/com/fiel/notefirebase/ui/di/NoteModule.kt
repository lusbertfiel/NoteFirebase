package com.fiel.notefirebase.ui.di


import com.fiel.notefirebase.ui.data.RepositoryImpl
import com.fiel.notefirebase.ui.domain.repository.Repository
import com.fiel.notefirebase.ui.domain.usecase.DeleteNote
import com.fiel.notefirebase.ui.domain.usecase.GetNotes
import com.fiel.notefirebase.ui.domain.usecase.InsertNote
import com.fiel.notefirebase.ui.domain.usecase.NoteUseCase
import com.fiel.notefirebase.ui.domain.usecase.UpdateNote
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object NoteModule {
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideRefNotes(db:FirebaseFirestore):CollectionReference=db.collection("Notas")

    @Provides
    fun provideRepository(impl:RepositoryImpl):Repository=impl

    @Provides
    fun provideNoteUseCase(repository: Repository)=NoteUseCase(
        getNotes = GetNotes(repository),
        deleteNote = DeleteNote(repository),
        updateNote = UpdateNote(repository = repository),
        insertNote = InsertNote(repository = repository)
    )

}