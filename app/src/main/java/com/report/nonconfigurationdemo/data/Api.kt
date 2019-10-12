package com.report.nonconfigurationdemo.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("cars/{id}")
    fun getSingleCar(@Path("id") id: Int): Single<Car?>
}