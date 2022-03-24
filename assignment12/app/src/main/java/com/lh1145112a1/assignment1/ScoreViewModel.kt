package com.lh1145112a1.assignment1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ScoreViewModel : ViewModel() {
    private val scores = MutableLiveData<List<Score>>()
    private val auth : FirebaseAuth

    init {
        auth = Firebase.auth
        val userID = auth.currentUser?.uid

        val db = FirebaseFirestore.getInstance().collection("scores")
            .whereEqualTo("uid", userID)
            .orderBy("scoreName")
            .addSnapshotListener{ documents, exception ->
                if (exception != null) {
                    Log.w("DB_Response", "Listen Failed ${exception.code}")
                    return@addSnapshotListener
                }
                Log.i("DB_Response", "# of documents = ${documents!!.size()}")
                documents?.let {
                    val scoreList = ArrayList<Score>()
                    for (document in documents) {
                        Log.i("DB_Response", "${document.data}")
                        val score = document.toObject(Score::class.java)
                        scoreList.add(score)
                    }
                    scores.value = scoreList
                }
            }
    }

    fun getScores() : LiveData<List<Score>> {
        return scores
    }
}