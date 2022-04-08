package com.lh1145112a1.assignment1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_home -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            R.id.action_add_score -> {
                startActivity(Intent(applicationContext, AddScoreActivity::class.java))
                return true
            }
            R.id.action_edit_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}