package com.android.airmart.worker

import android.content.Context
import com.google.gson.stream.JsonReader
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.airmart.data.AppDatabase
import com.android.airmart.data.entity.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

    override suspend fun doWork(): Result = coroutineScope {

        try {
            applicationContext.assets.open("users.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val userType = object : TypeToken<List<User>>() {}.type
                    val userList: List<User> = Gson().fromJson(jsonReader,userType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.userDao().insertUserList(userList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
}