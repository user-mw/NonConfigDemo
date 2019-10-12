package com.report.nonconfigurationdemo.domain

import com.report.nonconfigurationdemo.data.Api
import com.report.nonconfigurationdemo.data.ApiWorker
import com.report.nonconfigurationdemo.data.Car
import io.reactivex.Single

interface GetCarUseCase {
    operator fun invoke(id: Int): Single<Car?>
}

class GetCarUseCaseImpl : GetCarUseCase {

    private val api: Api = ApiWorker.getApiInstance()

    override fun invoke(id: Int): Single<Car?> =
        api.getSingleCar(id)
}