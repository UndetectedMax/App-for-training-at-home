package com.example.coursework.screens.adding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R
import com.example.coursework.repositories.OwnTrain.OwnTrainInfo
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class AddTrainingAdapter(
    options: FirebaseRecyclerOptions<OwnTrainInfo>,
    private val onItemClicked: (text: String) -> Unit
) : FirebaseRecyclerAdapter<OwnTrainInfo, AddTrainingAdapter.TrainViewHolder>(options) {

    class TrainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trainName: TextView = itemView.findViewById(R.id.train_name)
        val trainAuthor: TextView = itemView.findViewById(R.id.train_author)
        val trainCode: TextView = itemView.findViewById(R.id.train_code)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrainViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.own_train_item, parent, false)
        return TrainViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainViewHolder, position: Int, model: OwnTrainInfo) {
        holder.trainName.text = model.trainName
        holder.trainAuthor.text = model.trainAuthor
        holder.trainCode.text = model.trainCode
        holder.itemView.setOnClickListener {
            onItemClicked.invoke(holder.trainCode.text.toString())
        }
    }
}

