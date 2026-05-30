package com.example.myproject

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BiodataActivity : AppCompatActivity() {
    private lateinit var txtUsername: TextView
    private lateinit var btnKembali: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biodata)

        txtUsername = findViewById(R.id.txtUsername)
        btnKembali = findViewById(R.id.btnKembali)

        val username = intent.getStringExtra("USERNAME")
        txtUsername.text = "Username: $username"

        btnKembali.setOnClickListener {
            finish()
        }
    }
}