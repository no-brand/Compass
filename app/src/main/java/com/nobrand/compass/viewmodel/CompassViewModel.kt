package com.nobrand.compass.viewmodel

import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nobrand.compass.data.SensorCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class CompassViewModel @Inject constructor(
    private val manager: SensorManager?, private val listener: SensorCallback): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val degree: MutableLiveData<Float> by lazy {
        MutableLiveData<Float>()
    }
    val accelerometerAccuracy: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val magnetometerAccuracy: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val magneticStrength: MutableLiveData<Float> by lazy {
        MutableLiveData<Float>()
    }

    private fun handler() {
        compositeDisposable.add(
        Observable.combineLatest(listener.accelerometer, listener.magnetometer,
            BiFunction { acc, mag ->
                val R = FloatArray(9)
                val I = FloatArray(9)

                if (SensorManager.getRotationMatrix(R, I, acc, mag)) {
                    val orientation = FloatArray(3)
                    SensorManager.getOrientation(R, orientation)
                    return@BiFunction Math.toDegrees(orientation[0].toDouble()).toFloat()
                }
                return@BiFunction null
            })
            .subscribeOn(Schedulers.io())
            .filter { it != null }
            .subscribe({ v -> degree.postValue(v) }, { e -> })
        )
        compositeDisposable.addAll(
            listener.accelerometerAccuracy.subscribe({ v -> this.accelerometerAccuracy.postValue(v) }, { e -> }),
            listener.magnetometerAccuracy.subscribe({ v -> this.magnetometerAccuracy.postValue(v) }, { e -> })
        )
    }

    fun activate() {
        manager?.let {
            for (type in arrayOf(Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_MAGNETIC_FIELD)) {
                it.registerListener(listener as SensorEventListener,
                                    it.getDefaultSensor(type),
                                    SensorManager.SENSOR_DELAY_GAME)
            }
        }
        handler()
    }

    fun deactivate() {
        manager?.let {
            manager.unregisterListener(listener as SensorEventListener)
        }
        compositeDisposable.clear()
    }

}