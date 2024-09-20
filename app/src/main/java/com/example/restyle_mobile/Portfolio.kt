package com.example.us06_2

import Adapter.Adapter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Portfolio : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var addProjectButton: Button
    private lateinit var NombreEmpresa: TextView
    private lateinit var textoProyectos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.portfolio_main)


        // Inicialización de vistas
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        addProjectButton = findViewById(R.id.btnAddProject)
        NombreEmpresa = findViewById(R.id.txtNombreEmpresa)
        textoProyectos = findViewById(R.id.txtProyectos)

        // Configurar ViewPager con el adaptador para manejar las pestañas
        val adapter = Adapter(this)
        viewPager.adapter = adapter

        // Conectar TabLayout con ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "Información" else "Proyectos"
        }.attach()

        // Ocultar o mostrar el botón dependiendo de la pestaña seleccionada
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 1) {
                    addProjectButton.visibility = View.VISIBLE
                    NombreEmpresa.visibility = View.VISIBLE
                    textoProyectos.visibility = View.VISIBLE
                } else {
                    addProjectButton.visibility = View.GONE
                    NombreEmpresa.visibility = View.GONE
                    textoProyectos.visibility = View.GONE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        if (tabLayout.selectedTabPosition == 0) {
            addProjectButton.visibility = View.GONE
            NombreEmpresa.visibility = View.GONE
            textoProyectos.visibility = View.GONE
        }
    }
}
