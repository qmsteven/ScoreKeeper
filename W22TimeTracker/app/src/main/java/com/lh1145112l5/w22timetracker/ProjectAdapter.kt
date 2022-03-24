package com.lh1145112l5.w22timetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProjectAdapter (val context : Context,
                      val projects : List<Project>,
                      val itemListener : ProjectItemListener
                      ) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {
    /**
     * This class is used to allow us to connect/access the elements in the
     * item_project layout file
     */
    inner class ProjectViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val projectTextView = itemView.findViewById<TextView>(R.id.projectTextView)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)
    }

    /**
     * This connects (aka inflates) the individual viewHolder (which is the link to the item_project.xml)
     * with the RecyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_project, parent, false)
        return ProjectViewHolder(view)
    }

    /**
     * This method will bind the viewHolder with a specific project
     */
    override fun onBindViewHolder(viewHolder: ProjectViewHolder, position: Int) {
        val project = projects[position]
        with(viewHolder) {
            projectTextView.text = project.projectName
            descriptionTextView.text = project.description
            itemView.setOnClickListener {
                itemListener.projectSelected(project)
            }
        }
    }

    /**
     * This method returns the number of projects in the recycler view
     */
    override fun getItemCount(): Int {
        return projects.size
    }

    //In Java
    // public interface ProjectItemListener
    // {
    //     public projectSelected(Project project)
    // }
    interface ProjectItemListener {
        fun projectSelected(project : Project)
    }
}