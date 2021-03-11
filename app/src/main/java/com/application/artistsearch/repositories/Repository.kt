package com.application.artistsearch.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.artistsearch.service.ApiInterface
import com.example.sanathitunes.models.Model
import com.example.sanathitunes.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository() {

    private var apiInterface:ApiInterface?=null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }

    fun getResults(term:String): LiveData<Model> {
        val data = MutableLiveData<Model>()

        apiInterface?.getResults(term)?.enqueue(object : Callback<Model> {

            override fun onFailure(call: Call<Model>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<Model>,
                response: Response<Model>
            ) {

                val res = response.body()
                if (response.code() == 200 && res != null) {
                    data.value = res
                } else {
                    data.value = null
                }
            }
        })
        return data
    }
}