package com.lh1145112l5.w22timetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.Timestamp
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
        val db = FirebaseFirestore.getInstance().collection("projects")
        var project = Project();

        db.whereEqualTo("id", projectID)
            .get()
            .addOnSuccessListener { querySnapShot ->
                for (document in querySnapShot) {
                    project = document.toObject(Project::class.java)
                    binding.projectTextView.text = project.projectName
                }
            }

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
    }
}