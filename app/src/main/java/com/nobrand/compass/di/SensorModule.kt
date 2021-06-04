package com.nobrand.compass.di

import android.content.Context
import android.hardware.SensorManager
import com.nobrand.compass.data.SensorCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SensorModule {

    @Singleton
    @Provides
    fun provideSensorManager(@ApplicationContext context: Context): SensorManager? {
        return context.applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
    }

    @Singleton
    @Provides
    fun provideSensorEventListener(): SensorCallback {
        return SensorCallback()
    }
}