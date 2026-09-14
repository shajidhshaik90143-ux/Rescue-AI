package com.example.rescueai.ui.hospitals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rescueai.databinding.FragmentHospitalsBinding
import com.example.rescueai.ui.map.MapViewModel

class HospitalsFragment : Fragment() {

    private var _binding: FragmentHospitalsBinding? = null
    private val binding get() = _binding!!
    
    // Sharing the MapViewModel as it likely contains the location and hospital data logic
    private val viewModel: MapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHospitalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Setup RecyclerView with hospital adapter (to be implemented)
        viewModel.nearbyHospitals.observe(viewLifecycleOwner) { hospitals ->
            // Update list
        }
        
        viewModel.refreshLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
