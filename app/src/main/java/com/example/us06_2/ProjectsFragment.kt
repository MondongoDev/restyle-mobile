package com.example.us06_2

import Persistence.OpenHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProjectsFragment : Fragment() {
    lateinit var dbHelper: OpenHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el layout para el fragmento de Proyectos
        val view = inflater.inflate(R.layout.card, container, false)
        dbHelper = OpenHelper(requireContext())

        val listaP = dbHelper.getProjects()

        val recycler = view.findViewById<RecyclerView>(R.id.rvProjects)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = Adapter(listaP)

        return view
    }
}
