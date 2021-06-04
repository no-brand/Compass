package com.nobrand.compass.ui

import android.graphics.Color
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nobrand.compass.R
import com.nobrand.compass.databinding.FragmentCompassBinding
import com.nobrand.compass.viewmodel.CompassViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CompassFragment : Fragment() {

    companion object {
        fun newInstance() = CompassFragment()
    }

    private lateinit var binding: FragmentCompassBinding

    private val viewModel: CompassViewModel by viewModels()

    private var magnetometerCurrentAccuracy: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompassBinding.inflate(inflater, container, false)
                    this.onDraw()
                    this.onHandleAccuracyChanged()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.activate()
    }

    override fun onPause() {
        super.onPause()
        viewModel.deactivate()
    }

    private var current: Float = 0.0f

    private fun onDraw() {
        viewModel.degree.observe(viewLifecycleOwner, Observer {
            val animation = RotateAnimation(
                current,
                -it,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            animation.duration = 250
             animation.fillAfter = true

            binding.arrow.startAnimation(animation)
            current = -it
        })
    }

    private fun onHandleAccuracyChanged() {
        viewModel.magnetometerAccuracy.observe(viewLifecycleOwner, Observer {
            if (magnetometerCurrentAccuracy == it) return@Observer

            magnetometerCurrentAccuracy = it
            when (it) {
                SensorManager.SENSOR_STATUS_ACCURACY_LOW -> {
                    binding.arrow.setColorFilter(Color.RED)
                    binding.calibration.text = getText(R.string.calibration_lv1)
                }
                SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> {
                    binding.arrow.setColorFilter(Color.YELLOW)
                    binding.calibration.text = getText(R.string.calibration_lv2)
                }
                SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> {
                    binding.arrow.setColorFilter(Color.GREEN)
                    binding.calibration.text = getText(R.string.calibration_lv3)
                }
            }
        })

    }

}