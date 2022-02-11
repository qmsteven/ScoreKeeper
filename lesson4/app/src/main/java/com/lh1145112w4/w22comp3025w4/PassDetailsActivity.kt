package com.lh1145112w4.w22comp3025w4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lh1145112w4.w22comp3025w4.databinding.ActivityPassDetailsBinding

class PassDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPassDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get the persons name from the intent and update the TextView with the name
        var name = intent.getStringExtra("name")
        binding.nameTextView.text = "Hello $name"

        //update the button to navigate back to the MainActivity
        binding.detailsBackToMainButton.setOnClickListener {
            finish()
        }
    }
}