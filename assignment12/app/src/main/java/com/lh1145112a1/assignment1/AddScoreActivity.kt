package com.lh1145112l5.w22timetracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.lh1145112a1.assignment1.databinding.ActivityAddScoreBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.lh1145112a1.assignment1.*

class AddScoreActivity : AppCompatActivity(), ScoreAdapter.ScoreItemListener {
    //private ActivityCreateProjectBinding binding;  Java version of declaring the variable
    private lateinit var binding : ActivityAddScoreBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.addScoreButton.setOnClickListener {
            var projectName = binding.scoreNameText.text.toString().trim()
            var description = binding.descriptionEditText.text.toString().trim()

            if (projectName.isNotEmpty() && description.isNotEmpty()) {
                //connect to the Firestore DB
                val db = FirebaseFirestore.getInstance().collection("scores")

                //get a unique id from Firestore
                val id = db.document().getId()

                //create a Project object
                var uID = auth.currentUser!!.uid
                var score = Score(projectName, description, id, uID)

                //save our new Project object to the db using the unique id
                db.document(id).set(score)
                    .addOnSuccessListener {
                        Toast.makeText(this, "DB Updated", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { exception ->
                        Log.w(
                            "DB_Issue",
                            exception!!.localizedMessage
                        )
                    }
            } else
                Toast.makeText(this, "name and description must be filled in", Toast.LENGTH_LONG)
                    .show()
        }

        //connect RecyclerView with FirestoreDB via the ViewModel
        val viewModel: ScoreViewModel by viewModels()
        viewModel.getScores().observe(this, { projects ->
            binding.recyclerView.adapter = ScoreAdapter(this, projects, this)
//            binding.linearLayout.removeAllViews()
//            for (project in projects)
//            {
//                var newProjectTextView = TextView(this)
//                newProjectTextView.text = project.toString()
//                binding.linearLayout.addView(newProjectTextView)
//            }
        })

        //configure the toolbar to hold the main_menu
        setSupportActionBar(binding.mainToolBar.toolbar)
    }

    /**
     * Add the menu to the toolbar
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return true
            }
            R.id.action_add_score -> {
//                startActivity(Intent(applicationContext, AddScoreActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun scoreSelected(score: Score) {
        TODO("Not yet implemented")
    }
}