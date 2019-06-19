package com.android.airmart.data.api.model

import java.util.*

data class LoginResponse(val username:String,val token:String, val expirationDate:Date, val issuedDate:Date)