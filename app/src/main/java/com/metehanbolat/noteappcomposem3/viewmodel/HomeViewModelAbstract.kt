package com.metehanbolat.noteappcomposem3.viewmodel

import androidx.lifecycle.ViewModel
import com.metehanbolat.noteappcomposem3.data.repository.NoteRepository
import com.metehanbolat.noteappcomposem3.model.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeViewModelAbstract {
    val noteListFlow: Flow<List<NoteEntity>>
    fun addNote(note: NoteEntity)
    fun updateNote(note: NoteEntity)
    fun deleteNote(note: NoteEntity)
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel(), HomeViewModelAbstract {

    override val noteListFlow: Flow<List<NoteEntity>> = noteRepository.getAllFlow()

    override fun addNote(note: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch { noteRepository.insert(note = note) }
    }

    override fun updateNote(note: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch { noteRepository.update(note = note) }
    }

    override fun deleteNote(note: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch { noteRepository.delete(note = note) }
    }

}