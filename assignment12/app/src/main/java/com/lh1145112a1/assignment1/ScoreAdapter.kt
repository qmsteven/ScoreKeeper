package com.lh1145112a1.assignment1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lh1145112a1.assignment1.Score

class ScoreAdapter (val context : Context,
                    val scores : List<Score>,
                    val itemListener : ScoreItemListener
                    ) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {
    /**
     * This class is used to allow us to connect/access the elements in the
     * item_project layout file
     */
    inner class ScoreViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val scoreNameText = itemView.findViewById<TextView>(R.id.scoreNameText)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)
    }

    /**
     * This connects (aka inflates) the individual viewHolder (which is the link to the item_project.xml)
     * with the RecyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }

    /**
     * This method will bind the viewHolder with a specific project
     */
    override fun onBindViewHolder(viewHolder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        with(viewHolder) {
            scoreNameText.text = score.scoreName
            descriptionTextView.text = score.description
            itemView.setOnClickListener {
                itemListener.scoreSelected(score)
            }
        }
    }

    /**
     * This method returns the number of projects in the recycler view
     */
    override fun getItemCount(): Int {
        return scores.size
    }

    //In Java
    // public interface ProjectItemListener
    // {
    //     public projectSelected(Project project)
    // }
    interface ScoreItemListener {
        fun scoreSelected(score : Score)
    }
}