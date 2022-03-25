package com.lh1145112a1.assignment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.lh1145112a1.assignment1.databinding.ActivityManageScoreBinding

class ManageScoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityManageScoreBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityManageScoreBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.findButton.setOnClickListener {
            var scoreName = binding.scoreNameText.text.toString().trim()

            if (scoreName.isNotEmpty()) {
                val db = FirebaseFirestore.getInstance()

                val score = db.collection("scores").document(scoreName)

                var uID = auth.currentUser!!.uid

            }
            else
                Toast.makeText(this, "Name must be filled in", Toast.LENGTH_LONG)
                    .show()
        }

        setSupportActionBar(binding.mainToolBar.toolbar)
    }
}