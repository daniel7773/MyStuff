package com.example.testapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.adapters.HotelsAdapter
import com.example.testapplication.models.Hotel
import com.example.testapplication.viewmodels.HotelsViewModel

class MainActivity : AppCompatActivity() {

    lateinit var hotelsRecyclerView: RecyclerView
    lateinit var adapter: HotelsAdapter
    lateinit var hotelsViewModel: HotelsViewModel
    var hotelsList: List<Hotel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hotelsViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(HotelsViewModel::class.java)


        adapter = HotelsAdapter(this)
        hotelsRecyclerView = findViewById(R.id.hotels_rv)
        hotelsRecyclerView.adapter = adapter
        hotelsRecyclerView.layoutManager = LinearLayoutManager(this)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        hotelsViewModel.getHotels().observe(this, androidx.lifecycle.Observer { hotels ->
            if (hotels != null) {
                hotelsList = hotels;
                adapter.setItems(hotelsList)
            }
        })
    }
}
