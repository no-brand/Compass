package com.nobrand.compass.data

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import io.reactivex.rxjava3.subjects.BehaviorSubject


class SensorCallback : SensorEventListener {

    val accelerometer: BehaviorSubject<FloatArray> = BehaviorSubject.create()
    val magnetometer: BehaviorSubject<FloatArray> = BehaviorSubject.create()
    val accelerometerAccuracy: BehaviorSubject<Int> = BehaviorSubject.create()
    val magnetometerAccuracy: BehaviorSubject<Int> = BehaviorSubject.create()

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> this.accelerometer.onNext(event.values)
            Sensor.TYPE_MAGNETIC_FIELD -> this.magnetometer.onNext(event.values)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        when (sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> this.accelerometerAccuracy.onNext(accuracy)
            Sensor.TYPE_MAGNETIC_FIELD -> this.magnetometerAccuracy.onNext(accuracy)
        }
    }
}