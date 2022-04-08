package com.lh1145112a1.assignment1

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.set
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.lh1145112a1.assignment1.databinding.ActivityManageScoreBinding

class ManageScoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityManageScoreBinding
    private val scores = MutableLiveData<List<Score>>()
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityManageScoreBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()

        val viewModel: ScoreViewModel by viewModels()
        viewModel.getScores().observe(this, { scores ->
            binding.linearLayout.removeAllViews()
            for (score in scores) {
                var scoreDBName = score.toString()
                var newScoreTextView = TextView(this)
                newScoreTextView.text = score.toString() + ", " + score.toDescriptionString() + ". "
                var deleteButton = Button(this)
                deleteButton.text = "Delete"
                deleteButton.setOnClickListener {
                    db.collection("scores").document(scoreDBName)
                        .delete()
                        .addOnSuccessListener { Toast.makeText(this, "Database updated", Toast.LENGTH_LONG).show() }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error deleting document", e)
                            Toast.makeText(this, "Database not updated", Toast.LENGTH_LONG).show()
                        }
                }
                binding.linearLayout.addView(newScoreTextView)
                binding.linearLayout.addView(deleteButton)
            }
        })

        binding.findButton.setOnClickListener {
            var scoreName = binding.scoreNameText.text.toString().trim()
            Toast.makeText(this, "Score Name: $scoreName", Toast.LENGTH_LONG).show()

            if (scoreName.isNotEmpty()) {

            } else {
                Toast.makeText(this, "Name must be filled in", Toast.LENGTH_LONG).show()
            }
        }

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
                startActivity(Intent(applicationContext, AddScoreActivity::class.java))
                return true
            }
            R.id.action_edit_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}