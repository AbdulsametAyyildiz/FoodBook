package com.kalemlisipahi.foodbook.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.kalemlisipahi.foodbook.model.FoodModel
import com.kalemlisipahi.foodbook.servis.FoodDatabase
import kotlinx.coroutines.launch

class ContentFragmentViewModel(application: Application) : BaseViewModel(application){

    val foodList = MutableLiveData<FoodModel>()

    fun getRoomData(uuid : Int)
    {
        launch {
            val dataList = FoodDatabase(getApplication()).foodDAO().getFood(uuid)
            foodList.value = dataList
        }

    }

}