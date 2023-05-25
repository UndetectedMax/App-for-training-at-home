package com.example.coursework.screens.trainings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R

class TrainingAdapter(
    private val items: ArrayList<String>,
    var onItemClicked: (text: String) -> Unit
) :
    RecyclerView.Adapter<TrainingAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trainPreview: TextView = itemView.findViewById(R.id.recycler_view_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.trainPreview.text = items[position]
        holder.trainPreview.setOnClickListener {
            onItemClicked.invoke(holder.trainPreview.text.toString())
        }
    }
}
