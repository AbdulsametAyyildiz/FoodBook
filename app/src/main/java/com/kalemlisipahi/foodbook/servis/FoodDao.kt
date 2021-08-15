package com.kalemlisipahi.foodbook.servis

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kalemlisipahi.foodbook.model.FoodModel

@Dao
interface FoodDao {

    @Insert
    suspend fun insertAll(vararg food : FoodModel) : List<Long>

    @Query("SELECT * FROM foodmodel")
    suspend fun getAllFood(): List<FoodModel>

    @Query("SELECT * FROM foodmodel WHERE uuid = :foodId")
    suspend fun getFood(foodId : Int) : FoodModel

    @Query("DELETE FROM foodmodel")
    suspend fun  deleteAll()
}