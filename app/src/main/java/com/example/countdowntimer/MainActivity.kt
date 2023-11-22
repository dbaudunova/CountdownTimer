package com.example.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.countdowntimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        initObserver()
    }

    private fun initObserver() {
        viewModel.seconds().observe(this) {
            binding.tvNumber.text = it.toString()
        }
        viewModel.finished().observe(this) {
            if (it) {
                Toast.makeText(this, "Finished!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initListener() {
        binding.btnStart.setOnClickListener {
            if (binding.etMilliseconds.text.isEmpty() || binding.etMilliseconds.text.length < 4) {
                Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.timerValue.value = binding.etMilliseconds.text.toString().toLong()
                viewModel.startTimer()
            }
        }

        binding.btnStop.setOnClickListener {
            viewModel.stopTimer()
            Toast.makeText(this, "Countdown was stopped", Toast.LENGTH_SHORT).show()
        }

        binding.btnResume.setOnClickListener {
            viewModel.resumeTimer()
        }
    }
}