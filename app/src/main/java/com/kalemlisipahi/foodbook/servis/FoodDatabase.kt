package com.kalemlisipahi.foodbook.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kalemlisipahi.foodbook.model.FoodModel

@Database(entities = arrayOf(FoodModel::class),version = 1)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDAO() : FoodDao

    companion object
    {
        @Volatile private var instance : FoodDatabase? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, FoodDatabase::class.java, "fooddatabase").build()
    }

}