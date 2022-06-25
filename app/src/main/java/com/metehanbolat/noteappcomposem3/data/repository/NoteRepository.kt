package com.metehanbolat.noteappcomposem3.data.repository

import com.metehanbolat.noteappcomposem3.data.NoteDao
import com.metehanbolat.noteappcomposem3.model.NoteEntity
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val noteDao: NoteDao
) {
    fun getAllFlow(): Flow<List<NoteEntity>> = noteDao.getAllFlow()
    suspend fun insert(note: NoteEntity) = noteDao.insert(note = note)
    suspend fun update(note: NoteEntity) = noteDao.update(note = note)
    suspend fun delete(note: NoteEntity) = noteDao.delete(note = note)
}