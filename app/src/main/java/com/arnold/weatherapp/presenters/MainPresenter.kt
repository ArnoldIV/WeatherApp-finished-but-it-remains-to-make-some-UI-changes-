package com.arnold.weatherapp.presenters

import android.util.Log
import com.arnold.weatherapp.business.ApiProvider
import com.arnold.weatherapp.business.repos.MainRepository
import com.arnold.weatherapp.view.MainView

class MainPresenter : BasePresenter<MainView>() {
    private val repo = MainRepository(ApiProvider())

    override fun enable() {
        repo.dataEmitter
            .doAfterNext{viewState.setLoading(false)}
            .subscribe{ response ->
            Log.d(" MAINREPO", "Presenter enable(): $response")
            viewState.displayLocation(response.cityName)
            viewState.displayCurrentData(response.weatherData)
            viewState.displayDailyData(response.weatherData.daily)
            viewState.displayHourlyData(response.weatherData.hourly)
            response.error?.let{viewState.displayError(response.error)}
        }
    }

    fun refresh(lat: String, lon: String) {
        viewState.setLoading(true)
        repo.reloadData(lat,lon)
    }

}