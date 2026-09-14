package com.example.rescueai.ui.home

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rescueai.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private var pulseAnimator: ObjectAnimator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupPulseAnimation()

        // Use Long Click to prevent accidental SOS triggers
        binding.btnSos.setOnLongClickListener {
            viewModel.triggerSOS()
            Toast.makeText(context, "SOS Triggered! Help is on the way.", Toast.LENGTH_LONG).show()
            true
        }

        binding.btnSos.setOnClickListener {
            Toast.makeText(context, "Press and hold to trigger SOS", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupPulseAnimation() {
        // Create a heartbeat pulse animation for the background view
        pulseAnimator = ObjectAnimator.ofPropertyValuesHolder(
            binding.viewPulse,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 1.4f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 1.4f),
            PropertyValuesHolder.ofFloat(View.ALPHA, 0.4f, 0f)
        ).apply {
            duration = 1500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pulseAnimator?.cancel()
        _binding = null
    }
}
