package com.example.restyle_mobile.business_search

import Beans.Business
import Interface.BusinessService
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restyle_mobile.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchBusinessesActivity : AppCompatActivity() {

    lateinit var service: BusinessService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_businesses)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/alehandraxx/repo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<BusinessService>(BusinessService::class.java)
        getAllBusinesses()
    }

    private fun getAllBusinesses(){
        service.getBusinesses().enqueue(object : Callback<List<Business>>{
            override fun onResponse(p0: Call<List<Business>>, p1: Response<List<Business>>) {
                val business = p1?.body()

                val listBusinesses = mutableListOf<Business>()

                if(business != null){
                    for (item in business){
                        listBusinesses.add(
                            Business(
                                item.name,
                                item.description,
                                item.address,
                                item.city,
                                item.expertise,
                                item.image,
                                item.remodelerId
                            )
                        )
                    }
                    val recycler = findViewById<RecyclerView>(R.id.recyclerViewBusinesses)
                    recycler.layoutManager = LinearLayoutManager(applicationContext)
                    recycler.adapter = BusinessAdapter(listBusinesses)
                }
            }
            override fun onFailure(p0: Call<List<Business>>, p1: Throwable) {
                p1.printStackTrace()
            }
        })
    }
}