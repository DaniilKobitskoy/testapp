package com.app.testapp.network

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.app.testapp.model.LocationResponse
import com.app.testapp.view.MainActivity


class LocationCheckTask(private val context: Context) : AsyncTask<Void, Void, LocationResponse>() {

    override fun doInBackground(vararg params: Void?): LocationResponse? {
        val call = Client.locationApiService.getLocation()
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                return response.body()
            } else {
                Log.d("LocationCheckTask", "Query failed: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.d("LocationCheckTask", "Query failed: ${e.message}")
        }
        return null
    }

    override fun onPostExecute(result: LocationResponse?) {
        result?.let { locationResponse ->
            val countryCode = locationResponse.countryCode
            Log.d("LocationCheckTask", "Country code: $countryCode")

            when (countryCode) {
                "RU" -> {
                    if (context is MainActivity) {
                        val url = "https://google.com"
                        context.cloceview()
                        context.openWebViewFragment(url)

                    }
                }
                else -> {
                    if (context is MainActivity) {
                        context.cloceview()
                        context.openGameViewFragment()
                    }
                }
            }
        }
    }
}
