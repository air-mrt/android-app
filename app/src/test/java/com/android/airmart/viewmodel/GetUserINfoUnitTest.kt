package com.android.airmart.viewmodel

import com.android.airmart.data.entity.User
import com.android.airmart.repository.UserRepository
import com.android.airmart.viewmodel.DashboardViewModel
import org.junit.Before
import org.junit.Test

class GetUserINfoUnitTest {
    lateinit var userinf:DashboardViewModel

    @Before
    fun createClassInstance(){
    }
    @Test
    fun Testgetuserinfo(){
        val toke = "dj"
        val user = "user1"
        val result = userinf.getUserInfo(toke,user)
        assert(result.equals(true))
    }
}