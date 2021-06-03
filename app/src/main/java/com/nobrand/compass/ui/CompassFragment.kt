package com.nobrand.compass.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompassBinding.inflate(inflater, container, false)
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

}