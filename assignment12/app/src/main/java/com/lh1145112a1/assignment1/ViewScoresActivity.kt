package com.lh1145112a1.assignment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.lh1145112a1.assignment1.databinding.ActivityViewScoresBinding

class ViewScoresActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewScoresBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityViewScoresBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        val db = FirebaseFirestore.getInstance().collection("scores")
        val id = db.document().getId()

        var uID = auth.currentUser!!.uid

        val viewModel: ScoreViewModel by viewModels()
        viewModel.getScores().observe(this, { scores ->
            binding.linearLayout.removeAllViews()
            for (score in scores) {
                var newScoreTextView = TextView(this)
                newScoreTextView.text = score.toString() + ", " + score.toDescriptionString() + ". "
                binding.linearLayout.addView(newScoreTextView)
            }
        })
        setSupportActionBar(binding.mainToolBar.toolbar);
    }
}