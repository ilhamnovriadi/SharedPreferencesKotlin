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
    private lateinit var btnRoomDb: Button
    private lateinit var btnRestApi: Button
    private lateinit var switchDarkMode: SwitchCompat
    private lateinit var pref: SharedPreferences
    private lateinit var settingsPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_biodata)

        txtUsername = findViewById(R.id.txtUsername)
        btnLogout = findViewById(R.id.btnLogout)
        btnBukaPeta = findViewById(R.id.btnBukaPeta)
        btnRoomDb = findViewById(R.id.btnRoomDb)
        btnRestApi = findViewById(R.id.btnRestApi)
        switchDarkMode = findViewById(R.id.switchDarkMode)

        pref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)
        settingsPref = getSharedPreferences("SETTINGS_PREF", MODE_PRIVATE)

        val username = pref.getString("username", "-")
        txtUsername.text = "Username: $username"

        val isDarkMode = settingsPref.getBoolean("isDarkMode", false)
        switchDarkMode.isChecked = isDarkMode

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            settingsPref.edit().putBoolean("isDarkMode", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        btnLogout.setOnClickListener {
            pref.edit().clear().apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnBukaPeta.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        btnRoomDb.setOnClickListener {
            startActivity(Intent(this, NoteActivity::class.java))
        }

        btnRestApi.setOnClickListener {
            startActivity(Intent(this, PostActivity::class.java))
        }
    }
}