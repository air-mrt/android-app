package com.android.airmart.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.android.airmart.data.dao.*
import com.android.airmart.data.entity.*
import com.android.airmart.worker.SeedDatabaseWorker

@Database(entities = [Product::class, Comment::class, User::class, ChatEntity::class,ChatMessageEntity::class] , version = 13)
abstract class AppDatabase : RoomDatabase(){
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao


    //pre-populate database with dummy users...for checking
    companion object {
        @Volatile
        private var INSTANCE : AppDatabase? = null
        fun getInstance(context: Context):AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "airmart_database"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }})
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
