package com.kalemlisipahi.foodbook.viewmodel


import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.kalemlisipahi.foodbook.model.FoodModel
import com.kalemlisipahi.foodbook.servis.FoodDatabase
import com.kalemlisipahi.foodbook.servis.RestApiClient
import com.kalemlisipahi.foodbook.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.launch

class ListFragmentViewModel(application: Application) : BaseViewModel(application) {

    private val restApiClient = RestApiClient()
    private val disposable = CompositeDisposable()

    val foodListGlobal = MutableLiveData<List<FoodModel>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodLoading = MutableLiveData<Boolean>()

    val customSharedPreferences = CustomSharedPreferences(getApplication())

    fun refreshData()
    {
        if(customSharedPreferences.getTime() != 0L && System.nanoTime() - customSharedPreferences.getTime()!! < 10*60000000000L) getDataFromSQL()

        else getDataFromInternet()

    }

    fun refreshLayout() {getDataFromInternet()}

    private fun getDataFromSQL()
    {
        launch {
            val roomDataList = FoodDatabase(getApplication()).foodDAO().getAllFood()
            Toast.makeText(getApplication(),"Room Data",Toast.LENGTH_SHORT).show()
            showData(roomDataList)
        }
    }

    private fun getDataFromInternet()
    {
        foodErrorMessage.value = false
        foodLoading.value = true

        disposable.addAll(
            restApiClient
            .getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<FoodModel>>()
                {
                    override fun onSuccess(t: List<FoodModel>) {
                        saveSQL(t)
                        Toast.makeText(getApplication(),"Internet Data",Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(e: Throwable) {
                        foodLoading.value = false
                        foodErrorMessage.value = true
                    }

                })
        )
    }

    private fun showData(foodList: List<FoodModel>)
    {
        foodListGlobal.value = foodList
        foodLoading.value = false
        foodErrorMessage.value = false
    }

    private fun saveSQL(foodList : List<FoodModel>)
    {
        launch {
            val dao = FoodDatabase(getApplication()).foodDAO()
            dao.deleteAll()
            val uuidList = dao.insertAll(*foodList.toTypedArray())
            var i = 0
            while (i < foodList.size)
            {
                foodList[i].uuid = uuidList[i].toInt()
                i++
            }

            showData(foodList)
        }

        customSharedPreferences.saveTime(System.nanoTime())
    }
}