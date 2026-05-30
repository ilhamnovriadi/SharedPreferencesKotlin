package com.example.myproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button

    private lateinit var pref: SharedPreferences

    // Durasi sesi login: 24 jam (24 * 60 * 60 * 1000 milidetik)
    private val masaLogin: Long = 24 * 60 * 60 * 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inisialisasi Shared Preferences
        pref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)

        val isLogin = pref.getBoolean("isLogin", false)
        val waktuLogin = pref.getLong("waktuLogin", 0)
        val waktuSekarang = System.currentTimeMillis()

        // 2. Cek Status Login dan Masa Berlaku Sesi
        if (isLogin && waktuSekarang - waktuLogin < masaLogin) {
            // Jika masih login dan sesi belum habis, langsung ke BiodataActivity
            startActivity(Intent(this, BiodataActivity::class.java))
            finish()
            return
        } else if (isLogin && waktuSekarang - waktuLogin >= masaLogin) {
            // Jika sesi sudah habis, hapus data login
            pref.edit().clear().apply()
            Toast.makeText(this, "Sesi login telah habis, silakan login kembali", Toast.LENGTH_SHORT).show()
        }

        setContentView(R.layout.activity_main)

        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)

        // 3. Terapkan Logika Login dan Penyimpanan Sesi
        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            if (username == "admin" && password == "12345") {
                // Simpan status login dan data ke SharedPreferences
                pref.edit()
                    .putBoolean("isLogin", true)
                    .putString("username", username)
                    .putLong("waktuLogin", System.currentTimeMillis())
                    .apply()

                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()

                // Pindah ke BiodataActivity
                startActivity(Intent(this, BiodataActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}