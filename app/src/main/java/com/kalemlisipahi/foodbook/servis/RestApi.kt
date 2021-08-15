package com.kalemlisipahi.foodbook.servis

import com.kalemlisipahi.foodbook.model.FoodModel
import io.reactivex.Single
import retrofit2.http.GET

interface RestApi {

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getList() : Single<List<FoodModel>>

}