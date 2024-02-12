package com.fiel.notefirebase.ui.domain.usecase

import com.fiel.notefirebase.ui.domain.repository.Repository

class GetNotes(private val repository: Repository) {
    operator fun invoke()=repository.getNotes()
}