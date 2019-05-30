package com.android.airmart.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.airmart.data.dao.ProductDao
import com.android.airmart.data.entity.Product

@Database(entities = arrayOf(Product::class), version = 2)
abstract class AppDatabase : RoomDatabase(){
    abstract fun productDao(): ProductDao

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
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
