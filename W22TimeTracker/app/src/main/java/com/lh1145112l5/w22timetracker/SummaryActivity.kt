package com.lh1145112l5.w22timetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lh1145112l5.w22timetracker.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}