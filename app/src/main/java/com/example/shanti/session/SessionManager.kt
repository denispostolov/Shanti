package com.example.shanti.session

import android.content.SharedPreferences
import com.example.shanti.common.Constants

class SessionManager (
    private val preferences: SharedPreferences
) {
    fun setFirstAccess(){
        preferences.edit().putBoolean(Constants.FIRST_ACCESS_KEY, false).apply()
    }

    fun isFirstAccess(): Boolean {
        return preferences.getBoolean(Constants.FIRST_ACCESS_KEY, true)
    }

    fun setUserLoggedIn(){
        preferences.edit().putBoolean(Constants.LOGGED_USER_KEY, true).apply()
    }

    fun setUserUnLogged(){
        preferences.edit().putBoolean(Constants.LOGGED_USER_KEY, false).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return preferences.getBoolean(Constants.LOGGED_USER_KEY, false)
    }
}