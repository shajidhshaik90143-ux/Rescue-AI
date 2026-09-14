package com.example.rescueai.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rescueai.data.local.RescueDatabase
import com.example.rescueai.data.remote.RetrofitClient
import com.example.rescueai.data.repository.EmergencyRepository
import com.example.rescueai.databinding.FragmentEmergencyHistoryBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EmergencyHistoryFragment : Fragment() {

    private var _binding: FragmentEmergencyHistoryBinding? = null
    private val binding get() = _binding!!

    // In a real app, use Hilt to inject the repository
    private val repository by lazy {
        val database = RescueDatabase.getDatabase(requireContext())
        EmergencyRepository(database.emergencyDao(), RetrofitClient.instance)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencyHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvEmergencyHistory.layoutManager = LinearLayoutManager(requireContext())
        
        // Use a simple adapter or a specialized one to display History
        lifecycleScope.launch {
            repository.allEmergencies.collectLatest { list ->
                if (list.isEmpty()) {
                    binding.tvEmptyHistory.visibility = View.VISIBLE
                    binding.rvEmergencyHistory.visibility = View.GONE
                } else {
                    binding.tvEmptyHistory.visibility = View.GONE
                    binding.rvEmergencyHistory.visibility = View.VISIBLE
                    // Update adapter here
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
