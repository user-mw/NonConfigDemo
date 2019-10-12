package com.report.nonconfigurationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.report.nonconfigurationdemo.domain.GetCarUseCaseImpl
import com.report.nonconfigurationdemo.presentation.CarViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var carViewModel: CarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        carViewModel =
            lastCustomNonConfigurationInstance as? CarViewModel ?: CarViewModel(GetCarUseCaseImpl())

        carViewModel.text.observe(
            this, Observer<String> { text ->
                car_info.visibility = View.VISIBLE
                car_info.text = text
            })

        carViewModel.error.observe(
            this, Observer<Boolean> { errorVisible ->
                if (errorVisible) {
                    car_info.visibility = View.GONE
                    error_message.visibility = View.VISIBLE
                } else {
                    error_message.visibility = View.GONE
                }
            })

        carViewModel.inProgress.observe(
            this, Observer<Boolean> { loading ->
                if (loading) {
                    car_info.visibility = View.GONE
                    progress_title.visibility = View.VISIBLE
                } else {
                    progress_title.visibility = View.GONE
                }
            })
    }

    override fun onResume() {
        super.onResume()

        download_button.setOnClickListener {
            val textId = enter_id_field.text.toString()
            carViewModel.loadCar(textId)
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? = carViewModel

    /**
     *  You do not need to override this.
     *  It was added just for demo.
     */
    override fun getLastCustomNonConfigurationInstance(): Any? {
        return super.getLastCustomNonConfigurationInstance()
    }
}
