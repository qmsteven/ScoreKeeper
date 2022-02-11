package com.lh1145112a1.assignment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lh1145112a1.assignment1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}