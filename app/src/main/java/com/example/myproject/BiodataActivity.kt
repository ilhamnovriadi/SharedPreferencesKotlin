package com.example.myproject

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.content.SharedPreferences
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BiodataActivity : AppCompatActivity() {
    private lateinit var txtUsername: TextView
    private lateinit var btnLogout: Button
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_biodata)

        txtUsername = findViewById(R.id.txtUsername)
        btnLogout = findViewById(R.id.btnLogout)

        // 1. Inisialisasi Shared Preferences
        pref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)

        // 2. Ambil data username dari SharedPreferences
        val username = pref.getString("username", "-")
        txtUsername.text = "Username: $username"

        // 3. Terapkan Logika Logout
        btnLogout.setOnClickListener {
            // Hapus semua data yang tersimpan di SharedPreferences (Logout)
            pref.edit().clear().apply()

            // Kembali ke halaman Login (MainActivity)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}