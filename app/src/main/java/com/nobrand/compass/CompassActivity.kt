package com.nobrand.compass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nobrand.compass.ui.CompassFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CompassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                                  .replace(R.id.container, CompassFragment.newInstance())
                                  .commitNow()
        }
    }
}