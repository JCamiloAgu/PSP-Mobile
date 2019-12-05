package com.camilo.psp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.camilo.psp.FunctionsActivity
import com.camilo.psp.R
import com.camilo.psp.data.entity.ProjectEntity
import kotlinx.android.synthetic.main.projects_item.view.*

class ProjectsListAdapter (private val context: Context) : RecyclerView.Adapter<ProjectsListAdapter.ProjectViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var projects = emptyList<ProjectEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val itemView = inflater.inflate(R.layout.projects_item, parent, false)
        return ProjectViewHolder(itemView)
    }

    override fun getItemCount(): Int = projects.size

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val current = projects[position]
        holder.txtId.text = current.id.toString()
        holder.txtName.text = current.nameProject

    }

    internal fun setProjects(projects: List<ProjectEntity>)
    {
        this.projects = projects
        notifyDataSetChanged()
    }

    inner class ProjectViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.txtProjectName
        val txtId: TextView = itemView.txtProjectId

        private val cardView: View = itemView.cardViewProject

        init {
            cardView.setOnClickListener{
                val intent = Intent(context, FunctionsActivity::class.java)
                intent.putExtra("ProjectName", txtName.text)
                intent.putExtra("ProjectId", txtId.text)
                startActivity(context, intent, null)
            }
        }
    }
}