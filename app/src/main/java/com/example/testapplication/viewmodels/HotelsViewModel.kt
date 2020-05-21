package com.example.testapplication.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapplication.helpers.HotelDatabaseOpenHelper
import com.example.testapplication.models.Hotel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HotelsViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "HotelsViewModel"

    var hotelsLiveData: MutableLiveData<List<Hotel>>
    var hotelDBHelper: HotelDatabaseOpenHelper? = null

    init {
        hotelsLiveData = MediatorLiveData()
        initSearch(application)
    }

    override fun onCleared() {
        super.onCleared()

    }

    fun initSearch(context: Context) {
        hotelDBHelper = HotelDatabaseOpenHelper(context)

        val hotelsObservable: Observable<List<Hotel>> =
            Observable.just(hotelDBHelper!!.loadHotelsPage(2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        hotelsObservable.subscribe(
            {
                hotelsLiveData.postValue(it)
            },
            {
                Log.d(TAG, it.message)
            }
        )
    }

    fun getHotels(): MutableLiveData<List<Hotel>> {
        return hotelsLiveData
    }

}