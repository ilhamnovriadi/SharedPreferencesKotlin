package com.example.myproject

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Load the stored dark mode state and apply it globally
        val pref = getSharedPreferences("SETTINGS_PREF", MODE_PRIVATE)
        val isDarkMode = pref.getBoolean("isDarkMode", false)
        
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
