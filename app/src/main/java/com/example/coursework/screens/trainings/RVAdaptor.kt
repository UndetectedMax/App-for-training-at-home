package com.example.coursework.screens.trainings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil


import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R

class RVAdaptor(private val items: ArrayList<String>, private val context: Context, var onItemClicked:(text:String)-> Unit) :
    RecyclerView.Adapter<RVAdaptor.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trainPreview: TextView = itemView.findViewById(R.id.text)
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
        /*holder.trainPreview.setOnClickListener {
            Toast.makeText(context, "your chosen training is $position ", Toast.LENGTH_LONG).show()
        }*/
    }
}
