package com.lh1145112l5.w22timetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.google.firebase.auth.FirebaseAuth
import com.lh1145112l5.w22timetracker.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private val authDB = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //this allows the user to ... scroll
        binding.termsTextView.movementMethod = ScrollingMovementMethod()

        //ensure we have an authenticated user
        if (authDB.currentUser == null) {

        }
    }
}