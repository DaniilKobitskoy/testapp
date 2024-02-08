package com.app.testapp.network

import com.app.testapp.model.LocationResponse
import retrofit2.Call
import retrofit2.http.GET


interface LocationApiService {
    @GET("json/?fields=status,message,countryCode")
    fun getLocation(): Call<LocationResponse>
}

