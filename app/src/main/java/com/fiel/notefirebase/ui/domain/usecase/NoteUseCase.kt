package com.fiel.notefirebase.ui.domain.usecase

data class NoteUseCase (
    val getNotes: GetNotes,
    val insertNote: InsertNote,
    val updateNote: UpdateNote,
    val deleteNote: DeleteNote
)