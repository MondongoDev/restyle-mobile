package Interface

import Beans.Business
import retrofit2.Call
import retrofit2.http.GET

interface BusinessService {

    @GET("business")
    fun getBusinesses(): Call<List<Business>>
}