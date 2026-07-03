package com.example.myproject

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.content.SharedPreferences
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

class BiodataActivity : AppCompatActivity() {
    private lateinit var txtUsername: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnBukaPeta: Button
    private lateinit var switchDarkMode: SwitchCompat
    private lateinit var pref: SharedPreferences
    private lateinit var settingsPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_biodata)

        txtUsername = findViewById(R.id.txtUsername)
        btnLogout = findViewById(R.id.btnLogout)
        btnBukaPeta = findViewById(R.id.btnBukaPeta)
        switchDarkMode = findViewById(R.id.switchDarkMode)

        // 1. Inisialisasi Shared Preferences untuk Login
        pref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)

        // 2. Inisialisasi Shared Preferences untuk Pengaturan (Dark Mode)
        settingsPref = getSharedPreferences("SETTINGS_PREF", MODE_PRIVATE)

        // 3. Ambil data username dari SharedPreferences
        val username = pref.getString("username", "-")
        txtUsername.text = "Username: $username"

        // 4. Set status awal switch dark mode berdasarkan data SharedPreferences
        val isDarkMode = settingsPref.getBoolean("isDarkMode", false)
        switchDarkMode.isChecked = isDarkMode

        // 5. Terapkan Logika Toggle Dark Mode
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            settingsPref.edit().putBoolean("isDarkMode", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // 6. Terapkan Logika Logout
        btnLogout.setOnClickListener {
            // Hapus semua data yang tersimpan di SharedPreferences (Logout)
            pref.edit().clear().apply()

            // Kembali ke halaman Login (MainActivity)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnBukaPeta.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}