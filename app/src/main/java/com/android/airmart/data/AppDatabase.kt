package com.android.airmart.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.android.airmart.data.dao.CommentDao
import com.android.airmart.data.dao.ProductDao
import com.android.airmart.data.dao.UserDao
import com.android.airmart.data.entity.Comment
import com.android.airmart.data.entity.Product
import com.android.airmart.data.entity.User
import com.android.airmart.worker.SeedDatabaseWorker

@Database(entities = [Product::class, Comment::class, User::class], version = 10)
abstract class AppDatabase : RoomDatabase(){
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao


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
