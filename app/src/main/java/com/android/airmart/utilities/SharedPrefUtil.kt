package com.android.airmart.utilities

import android.content.SharedPreferences

object SharedPrefUtil {
    fun savePreference(sharedPref: SharedPreferences,token:String,username:String,password:String,isLoggedIn:Boolean){
        with(sharedPref.edit()){
            putString(TOKEN_KEY,token)
            putString(USERNAME_KEY,username)
            putString(PASSWORD_KEY,password)
            putBoolean(ISLOGGEDIN_KEY,isLoggedIn)
            commit()
        }
    }
    fun getToken(sharedPref: SharedPreferences):String{
        return """Bearer ${sharedPref.getString(TOKEN_KEY,DEFAULT_VALUE_SHARED_PREF)}"""
    }
    fun getUsername(sharedPref: SharedPreferences):String{
        return sharedPref.getString(USERNAME_KEY, DEFAULT_VALUE_SHARED_PREF)

    }
}