package com.example.restyle_mobile.business_search.Activity

import Beans.Business
import Interface.BusinessService
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.restyle_mobile.R
import com.example.restyle_mobile.business_search.Adapter.BusinessProfilePagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusinessProfileActivity : AppCompatActivity() {

    private lateinit var businessService: BusinessService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_business_profile)

        //Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //Navigation Bar
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, SearchBusinessesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_projects -> {
                    val intent = Intent(this, SearchBusinessesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_businesses -> {
                    val intent = Intent(this, SearchBusinessesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_portfolios -> {
                    val intent = Intent(this, SearchBusinessesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, SearchBusinessesActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        //Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.18.175:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        businessService = retrofit.create<BusinessService>(BusinessService::class.java)

        val businessId = intent.getIntExtra("BUSINESS_ID", -1)
        if (businessId != -1) {
            getBusinessById(businessId)
        }

        val tabLayout = findViewById<TabLayout>(R.id.business_profile_tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.business_profile_view_pager)

        val adapter = BusinessProfilePagerAdapter(this, businessId)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Perfil"
                1 -> tab.text = "Proyectos"
                2 -> tab.text = "Portafolio"
            }
        }.attach()
    }

    private fun getBusinessById(businessId: Int){
        businessService.getBusinessById(businessId).enqueue(object : Callback<Business> {
            override fun onResponse(p0: Call<Business>, p1: Response<Business>) {
                val business = p1?.body()

                if(business != null){
                    findViewById<TextView>(R.id.businessCardName).text = business.name
                    findViewById<TextView>(R.id.businessCardAddress).text = business.address
                    findViewById<TextView>(R.id.businessCardCity).text = business.city
                    findViewById<TextView>(R.id.businessCardExpertise).text = business.expertise
                }
            }
            override fun onFailure(p0: Call<Business>, p1: Throwable) {
                p1.printStackTrace()
            }
        })
    }
}