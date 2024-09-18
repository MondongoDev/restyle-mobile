package com.example.restyle_mobile.business_search

import Beans.Business
import Beans.Project
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restyle_mobile.R
import com.squareup.picasso.Picasso

class CardViewHolder(view: View):RecyclerView.ViewHolder(view) {

    val businessName = view.findViewById<TextView>(R.id.businessCardName)
    val businessExpertise = view.findViewById<TextView>(R.id.businessCardExpertise)
    //val projectName = view.findViewById<TextView>(R.id.projectCardName)
    val projectImage = view.findViewById<ImageView>(R.id.projectCardImage)

    fun render(businessModel: Business){
        businessName.text = businessModel.name
        businessExpertise.text = businessModel.expertise
        //projectName.text = projectModel.name
        Picasso.get().load(businessModel.image)
            .resize(300,300)
            .centerCrop().into(projectImage)
    }

}