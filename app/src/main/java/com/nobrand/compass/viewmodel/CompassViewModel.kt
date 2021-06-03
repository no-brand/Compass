package com.nobrand.compass.viewmodel

import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CompassViewModel @Inject constructor(
    private val manager: SensorManager?, private val listener: SensorEventListener): ViewModel() {

    fun activate() {
        manager?.let {
            for (type in arrayOf(Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)) {
                it.registerListener(listener, it.getDefaultSensor(type), SensorManager.SENSOR_DELAY_GAME)
            }
        }
    }

    fun deactivate() {
        manager?.let {
            manager.unregisterListener(listener)
        }
    }

}