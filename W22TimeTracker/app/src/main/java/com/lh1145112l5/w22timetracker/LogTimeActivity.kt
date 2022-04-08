package com.lh1145112l5.w22timetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lh1145112l5.w22timetracker.databinding.ActivityLogTimeBinding

class LogTimeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLogTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get the course information and update the header
        val projectID = intent.getStringExtra("projectID")
        val latLng = intent.getStringExtra("latLng")
        latLng?.let{
            Log.i("location", "returned from maps -> $latLng")
        }
        if (projectID == null) {
            Toast.makeText(this, "Select a Project to Log time", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, CreateProjectActivity::class.java))
        }
        val db = FirebaseFirestore.getInstance().collection("projects")
        var project = Project();

        //query the firestore database to get all the projects for the active user
        val userID = FirebaseAuth.getInstance().currentUser!!.uid

        if (userID == null) {
            finish()
            startActivity(Intent(applicationContext, SigninActivity::class.java))
        }

        //create a mutable (changeable) list of Project objects
        val projects: MutableList<Project?> = ArrayList()

        //create an adapter for the spinner so that we can change the ArrayList in the spinner
        val adapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, projects)
        binding.projectSelectSpinner.adapter = adapter

        db.whereEqualTo("uid", userID)
            .orderBy("projectName")
            .get()
            .addOnSuccessListener { querySnapshot ->
                projects.add(Project(projectName = "Choose a Project"))
                for (document in querySnapshot) {
                    val project = document.toObject(Project::class.java)
                    projects.add(project)

                    /*projectID?.let {
                        val project = getPositionInProjects(projectID, projects)
                        binding.projectSelectSpinner.setSelection(project)
                    }*/
                }
                adapter.notifyDataSetChanged()
            }

        //create a "listener" for the spinner
        binding.projectSelectSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.i(
                        "Spinner",
                        "position = $position, which is project: ${projects.get(position)}"
                    )

                    val projectSelected = projects.get(position)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
                }

            }


        /*db.whereEqualTo("id", projectID)
            .get()
            .addOnSuccessListener { querySnapShot ->
                for (document in querySnapShot) {
                    project = document.toObject(Project::class.java)
                    binding.projectTextView.text = project.projectName
                }
            }*/

        //variables to store the start and stop time
        var startTime : Timestamp? = null
        var finishTime : Timestamp? = null
        var category : String? = null

        //when the start button is selected, show the time
        binding.startButton.setOnClickListener {
            if (binding.startTextView.text.toString().isNullOrBlank()) {
                startTime = Timestamp.now()
                binding.startTextView.text = startTime!!.toDate().toString()
            }
        }

        //when the finish button is selected, create a TimeRecord object and store it in firestore
        binding.finishButton.setOnClickListener {
            if (startTime != null && binding.spinner.selectedItemPosition>0) {
                category = binding.spinner.selectedItem.toString()
                finishTime = Timestamp.now()
                binding.finishTextView.text = finishTime!!.toDate().toString()

                val timeRecord = TimeRecord(category, startTime, finishTime)
                binding.totalTimeTextView.text = String.format("Total time : %d minutes", timeRecord.getDuration())

                //add TimeRecord to the project and update the Firestore DB
                project.addTimeRecord(timeRecord)
                project?.let {
                    db.document(project.id!!).set(project)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "DB Updated",
                                Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                this,
                                "DB write failed",
                                Toast.LENGTH_LONG).show()
                        }
                }
            }
            else {
                Toast.makeText(this, "Start time & category are both required", Toast.LENGTH_LONG).show()
            }
        }

        binding.mapsButton.setOnClickListener {
            var intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("projectID", projectID)
            startActivity(intent)
        }

        setSupportActionBar(binding.mainToolBar.toolbar)
    }

    /*private fun getPositionInProjects(projectID: String, projects : ArrayList<Project>): Project {
        for (project in projects) {
            if (project.id.equals(projectID)) {
                return project
            }
        }
        return null
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_log_time -> {
                startActivity(Intent(applicationContext, LogTimeActivity::class.java))
                return true
            }
            R.id.action_add_project -> {
//                startActivity(Intent(applicationContext, CreateProjectActivity::class.java))
                return true
            }
            R.id.action_view_summary -> {
                //page to be created
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