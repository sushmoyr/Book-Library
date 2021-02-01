package com.sushmoyr.booklibrary.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao() : BookDao

    companion object{
        @Volatile
        private var INSTANCE : BookDatabase? = null

        fun getDatabase(context: Context) : BookDatabase
        {
            val tempInstance = INSTANCE
            if(tempInstance != null)
                return tempInstance
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}