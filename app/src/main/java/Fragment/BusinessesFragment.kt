package Fragment

import Adapter.ProjectAdapter
import Persistence.OpenHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.us06_2.R

class BusinessesFragment : Fragment() {
    lateinit var dbHelper: OpenHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.business_info, container, false)
        dbHelper = OpenHelper(requireContext())

        val business = dbHelper.getBusinessesById(1)

        if (business != null) {
            val businessId = view.findViewById<TextView>(R.id.bussinessId)
            val businessDescription = view.findViewById<TextView>(R.id.txtBusinessDescription)
            val businessImage = view.findViewById<ImageView>(R.id.imgBusiness)
            val recyclerView = view.findViewById<RecyclerView>(R.id.rvProjects)

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = ProjectAdapter(dbHelper.getProjects())

            businessId.text = business.id.toString()
            businessDescription.text = business.description

            Glide.with(this)
                .load(business.image)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(businessImage)
        }

        return view
    }
}
