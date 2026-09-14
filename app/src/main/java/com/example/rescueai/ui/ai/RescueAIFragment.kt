package com.example.rescueai.ui.ai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rescueai.databinding.FragmentRescueAiBinding

class RescueAIFragment : Fragment() {

    private var _binding: FragmentRescueAiBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RescueAIViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRescueAiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.btnAskAi.setOnClickListener {
            val query = binding.etAiQuery.text.toString()
            if (query.isNotEmpty()) {
                viewModel.getAdvice(query)
            }
        }

        viewModel.aiResponse.observe(viewLifecycleOwner) { advice ->
            binding.tvAiResponse.text = advice
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
