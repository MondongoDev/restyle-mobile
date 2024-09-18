package com.example.restyle_mobile.business_search

import Beans.Business
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restyle_mobile.R

class BusinessAdapter(val businessList: List<Business>):RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return CardViewHolder(layoutInflater.inflate(R.layout.business_card, parent, false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.render(businessList[position])
    }

    override fun getItemCount(): Int {
        return businessList.size
    }
}