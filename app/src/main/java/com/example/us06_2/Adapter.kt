package com.example.us06_2

import Beans.Projects
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter(val projectsList:List<Projects>): RecyclerView.Adapter<ProjectsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return ProjectsViewHolder(layoutInflater.inflate(R.layout.card, parent, false))
    }

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        holder.render(projectsList[position])
    }

    override fun getItemCount(): Int {
        return projectsList.size
    }
}