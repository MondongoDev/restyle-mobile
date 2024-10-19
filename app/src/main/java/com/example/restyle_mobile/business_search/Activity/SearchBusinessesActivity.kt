package com.example.restyle_mobile.business_search.Activity

import Beans.Business
import Beans.Businesses
import Interface.BusinessService
import Interface.ProjectService
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Fragment.DialogLoadingFragment
import com.example.restyle_mobile.AuthInterceptor
import com.example.restyle_mobile.Beans.SignInRequest
import com.example.restyle_mobile.BottomNavigationHelper
import com.example.restyle_mobile.Interface.AuthService
import com.example.restyle_mobile.R
import com.example.restyle_mobile.business_portfolio.Activity.Portfolio
import com.example.restyle_mobile.business_search.Adapter.BusinessAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchBusinessesActivity : AppCompatActivity() {

    lateinit var businessService: BusinessService

    fun provideRetrofitWithAuth(token: String): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()

        return Retrofit.Builder()
            .baseUrl("https://restyle-backend-v2.zeabur.app/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_businesses)

        //Loading Fragment
        val loadingDialog = DialogLoadingFragment.newInstance()
        loadingDialog.show(supportFragmentManager, DialogLoadingFragment.TAG)

        //Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //Navigation Bar
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        BottomNavigationHelper().setupBottomNavigation(this, bottomNavigationView)

        // Configurar Retrofit para sign-in sin autenticación
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restyle-backend-v2.zeabur.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear AuthService
        val authService = retrofit.create(AuthService::class.java)

        // Realizar el sign-in y obtener el token
        GlobalScope.launch(Dispatchers.IO) {
            val signInRequest = SignInRequest("admin", "admin")
            val response = authService.signIn(signInRequest)

            if (response.isSuccessful) {
                val token = response.body()?.token
                token?.let {
                    // Crear Retrofit con autenticación
                    val retrofitWithAuth = provideRetrofitWithAuth(it)

                    // Inicializar el servicio de negocios con autenticación
                    businessService = retrofitWithAuth.create(BusinessService::class.java)

                    // Obtener los negocios y actualizar la UI
                    getAllBusinesses()

                    Handler(Looper.getMainLooper()).postDelayed({
                        // Ocultar el DialogFragment cuando el backend haya respondido
                        loadingDialog.dismiss()
                        // Continuar con el flujo de la aplicación
                    }, 2000)
                }
            } else {
                println("Error en el sign-in: ${response.errorBody()?.string()}")
            }
        }
    }

    // Función para obtener y mostrar los negocios
    private fun getAllBusinesses() {
        // La llamada ahora se realiza correctamente con autenticación
        businessService.getBusinesses().enqueue(object : Callback<List<Businesses>> {
            override fun onResponse(call: Call<List<Businesses>>, response: Response<List<Businesses>>) {
                val businesses = response.body()

                val listBusinesses = mutableListOf<Businesses>()

                if (businesses != null) {
                    for (item in businesses) {
                        listBusinesses.add(
                            Businesses(
                                item.id,
                                item.address,
                                item.city,
                                item.description,
                                item.expertise,
                                item.image,
                                item.name,
                                item.remodeler_id
                            )
                        )
                    }

                    // Actualizar RecyclerView en el hilo principal
                    runOnUiThread {
                        val recycler = findViewById<RecyclerView>(R.id.recyclerViewBusinesses)
                        recycler.layoutManager = LinearLayoutManager(applicationContext)
                        recycler.adapter = BusinessAdapter(listBusinesses) { business ->
                            val intent = Intent(this@SearchBusinessesActivity, BusinessProfileActivity::class.java)
                            intent.putExtra("BUSINESS_ID", business.id)
                            startActivity(intent)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Businesses>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}