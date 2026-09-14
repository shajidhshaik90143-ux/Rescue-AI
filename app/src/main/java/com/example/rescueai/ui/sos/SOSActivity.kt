package com.example.rescueai.ui.sos

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rescueai.databinding.ActivitySosBinding

class SOSActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySosBinding
    private val viewModel: SOSViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancelSos.setOnClickListener {
            viewModel.cancelSOS()
            finish()
        }

        viewModel.sosStatus.observe(this) { status ->
            binding.tvSosStatus.text = status
        }
    }
}
