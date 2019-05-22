package com.android.airmartversion1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.airmartversion1.data.dao.ProductDao
import com.android.airmartversion1.data.entity.Product

@Database(entities = arrayOf(Product::class), version = 1)
abstract class AirmartDatabase : RoomDatabase(){
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE : AirmartDatabase? = null
        fun getDatabase(context: Context):AirmartDatabase{
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AirmartDatabase::class.java, "airmart_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
