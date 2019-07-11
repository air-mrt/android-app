package com.android.airmart.viewmodel

import androidx.lifecycle.MutableLiveData
import com.android.airmart.utilities.AuthenticationState
import com.android.airmart.utilities.AuthenticationState.*
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthenticationTest (){
    lateinit var authenticationState :MutableLiveData<AuthenticationState>
    @Before
    fun setup(){
        authenticationState = MutableLiveData()
    }
    @Test
    fun refuseauthTest(){
        authenticationState.value = INVALID_AUTHENTICATION
        assertEquals(authenticationState,true)
    }
    @Test
    fun susAuthTest(){
        authenticationState.value = AUTHENTICATED
        assertEquals(authenticationState,true)
    }
    @Test
    fun erurAuthTest(){
        authenticationState.value = EXPIRED_TOKEN
        assertEquals(authenticationState,true)
    }
}