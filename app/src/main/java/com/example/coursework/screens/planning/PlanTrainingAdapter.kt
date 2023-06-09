package com.example.coursework.screens.planning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R
import com.example.coursework.repositories.OwnTrain.OwnTrainInfo
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class PlanTrainingAdapter(
    options: FirebaseRecyclerOptions<OwnTrainInfo>,
    private val onItemClicked: (text: String) -> Unit
) : FirebaseRecyclerAdapter<OwnTrainInfo, PlanTrainingAdapter.TrainViewHolder>(options) {

    class TrainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trainName: TextView = itemView.findViewById(R.id.name_of_train)
        val trainAuthor: TextView = itemView.findViewById(R.id.author_of_train)
        val trainCode: TextView = itemView.findViewById(R.id.code_of_train)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.plan_train_item, parent, false)
        return TrainViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainViewHolder, position: Int, model: OwnTrainInfo) {
        holder.trainName.text = model.trainName
        holder.trainAuthor.text = model.trainAuthor
        holder.trainCode.text = model.trainCode
        holder.itemView.setOnClickListener{
            onItemClicked.invoke(holder.trainCode.text.toString())
        }
    }
}
