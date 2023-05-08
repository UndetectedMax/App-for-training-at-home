package com.example.coursework.screens.adding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R
import com.example.coursework.repositories.OwnTrain.OwnTrainInfo
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class AddTrainingAdapter(
    private val options: FirestoreRecyclerOptions<OwnTrainInfo>,
    ) : FirestoreRecyclerAdapter<OwnTrainInfo, AddTrainingAdapter.TrainViewHolder>(options) {

    inner class TrainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val trainName: TextView = itemView.findViewById(R.id.name_of_train)
        private val trainAuthor: TextView = itemView.findViewById(R.id.train_author)
        private val trainCode: TextView = itemView.findViewById(R.id.train_code)

        fun bind(train: OwnTrainInfo) {
            trainName.text = train.trainName.toString()
            trainAuthor.text = train.trainAuthor.toString()
            trainCode.text = train.trainCode.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainViewHolder {
        val holder = TrainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.own_train_item,
                parent,
                false
            )
        )
           /* holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(
                options.snapshots[holder.adapterPosition]
            )
        }*/
        return holder
    }

    override fun onBindViewHolder(holder: TrainViewHolder, position: Int, model: OwnTrainInfo) {
        holder.bind(model)
    }

    /*interface OnItemClickListener {
        fun onItemClick(ownTrain: OwnTrainInfo)
    }*/
}

