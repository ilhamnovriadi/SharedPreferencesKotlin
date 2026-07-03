package com.example.myproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteActivity : AppCompatActivity() {

    private lateinit var edtNoteTitle: EditText
    private lateinit var edtNoteContent: EditText
    private lateinit var btnSaveNote: Button
    private lateinit var rvNotes: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        edtNoteTitle = findViewById(R.id.edtNoteTitle)
        edtNoteContent = findViewById(R.id.edtNoteContent)
        btnSaveNote = findViewById(R.id.btnSaveNote)
        rvNotes = findViewById(R.id.rvNotes)

        database = AppDatabase.getDatabase(this)
        rvNotes.layoutManager = LinearLayoutManager(this)

        noteAdapter = NoteAdapter(emptyList()) { note ->
            deleteNote(note)
        }
        rvNotes.adapter = noteAdapter

        btnSaveNote.setOnClickListener {
            saveNote()
        }

        loadNotes()
    }

    private fun loadNotes() {
        CoroutineScope(Dispatchers.Main).launch {
            val notes = withContext(Dispatchers.IO) {
                database.noteDao().getAll()
            }
            noteAdapter.updateNotes(notes)
        }
    }

    private fun saveNote() {
        val title = edtNoteTitle.text.toString().trim()
        val content = edtNoteContent.text.toString().trim()

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val note = Note(title = title, content = content)
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                database.noteDao().insert(note)
            }
            edtNoteTitle.text.clear()
            edtNoteContent.text.clear()
            loadNotes()
        }
    }

    private fun deleteNote(note: Note) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                database.noteDao().delete(note)
            }
            loadNotes()
        }
    }
}
