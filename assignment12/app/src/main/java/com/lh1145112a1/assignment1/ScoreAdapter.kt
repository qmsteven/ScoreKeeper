package com.lh1145112a1.assignment1

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreAdapter (val context : Context,
                    val scores : List<Score>,
                    val itemListener : ScoreItemListener
                    ) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {
    inner class ScoreViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val scoreTextView = itemView.findViewById<TextView>(R.id.scoreNameText)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionEditText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        with(viewHolder){
            scoreTextView.text = score.scoreName
            descriptionTextView.text = score.description
            itemView.setOnClickListener {
                itemListener.scoreSelected(score)
            }
        }
    }

    override fun getItemCount(): Int {
        return scores.size
    }

    interface ScoreItemListener {
        fun scoreSelected(score : Score)
    }
}