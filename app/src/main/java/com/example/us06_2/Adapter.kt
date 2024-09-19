package com.example.us06_2

import Beans.Projects
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class Adapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2 // Número de pestañas
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            InfoFragment() // Fragmento para Información
        } else {
            ProjectsFragment() // Fragmento para Proyectos
        }
    }
}