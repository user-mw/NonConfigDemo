package com.report.nonconfigurationdemo.data

import com.google.gson.Gson
import com.report.nonconfigurationdemo.BuildConfig.MAIN_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiWorker {

    companion object {
        private val gsonInstance: Gson = Gson()

        fun getApiInstance(): Api =
            getRetrofitInstance().create(Api::class.java)

        private fun getOkHttp(): OkHttpClient =
            OkHttpClient()
                .newBuilder()
                .build()

        private fun getRetrofitInstance(): Retrofit =
            Retrofit.Builder()
                .baseUrl(MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonInstance))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}