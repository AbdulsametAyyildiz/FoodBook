package com.kalemlisipahi.foodbook.servis

import com.kalemlisipahi.foodbook.model.FoodModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestApiClient (){

    private val BASE_URL : String = "https://raw.githubusercontent.com/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RestApi::class.java)


    fun getData(): Single<List<FoodModel>> {return api.getList()}
}