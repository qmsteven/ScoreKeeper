package com.lh1145112w1.w22comp3025w1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lh1145112w1.w22comp3025w1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            binding.greetingsTextView.text = "Hello " + name
        }
    }
}