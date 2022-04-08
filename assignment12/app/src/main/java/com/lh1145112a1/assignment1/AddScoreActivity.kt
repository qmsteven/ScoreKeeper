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
import com.lh1145112a1.assignment1.databinding.ActivityAddScoreBinding

class AddScoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddScoreBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddScoreBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.addScoreButton.setOnClickListener {
            var scoreName = binding.scoreNameText.text.toString().trim()
            var description = binding.descriptionEditText.text.toString().trim()

            if (scoreName.isNotEmpty() && description.isNotEmpty()) {
                val db = FirebaseFirestore.getInstance().collection("scores")

                val id = db.document().getId()

                var uID = auth.currentUser!!.uid
                var score = Score(scoreName, description, id, uID)

                db.document(id).set(score)
                    .addOnSuccessListener {
                        Toast.makeText(this, "DB Updated", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { exception ->
                        Log.w("DB_Issue", exception!!.localizedMessage)
                    }
            }
            else {
                Toast.makeText(this, "Name and description must be filled in", Toast.LENGTH_LONG).show()
            }
        }

        val viewModel: ScoreViewModel by viewModels()
        viewModel.getScores().observe(this, { scores ->
            binding.linearLayout.removeAllViews()
            for (score in scores) {
                var newScoreTextView = TextView(this)
                newScoreTextView.text = score.toString() + ", " + score.toDescriptionString() + ". "
                binding.linearLayout.addView(newScoreTextView)
            }
        })

        setSupportActionBar(binding.mainToolBar.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            R.id.action_add_new_score -> {
//                startActivity(Intent(applicationContext, AddScoreActivity::class.java))
                return true
            }
            R.id.action_edit_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}