package com.report.nonconfigurationdemo.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.report.nonconfigurationdemo.data.Car
import com.report.nonconfigurationdemo.domain.GetCarUseCase
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CarViewModel(private val getCarUseCase: GetCarUseCase) : ViewModel() {

    val text: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<Boolean> = MutableLiveData()
    val inProgress: MutableLiveData<Boolean> = MutableLiveData()

    fun loadCar(stringId: String) {
        if (stringId.isNotBlank() && stringId.isNotEmpty()) {
            val id = Integer.parseInt(stringId)

            getCarUseCase(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Car?> {
                    override fun onSuccess(car: Car) {
                        inProgress.postValue(false)

                        val textResult = """
                            Brand: ${car.brand}
                            Model: ${car.model}
                            Color: ${car.color}
                            Year: ${car.year}
                            Cost: ${car.cost}
                        """.trimIndent()

                        text.postValue(textResult)
                    }

                    override fun onSubscribe(d: Disposable) {
                        error.postValue(false)
                        inProgress.postValue(true)
                    }

                    override fun onError(e: Throwable) {
                        inProgress.postValue(false)
                        error.postValue(true)
                    }
                })
        } else {
            error.postValue(true)
        }
    }
}