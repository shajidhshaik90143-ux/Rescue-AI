package com.example.rescueai.ui.contacts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rescueai.databinding.FragmentEmergencyContactsBinding

class EmergencyContactsFragment : Fragment() {

    private var _binding: FragmentEmergencyContactsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencyContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddContact.setOnClickListener {
            startActivity(Intent(requireContext(), AddContactActivity::class.java))
        }
        
        // Setup RecyclerView for contacts
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
