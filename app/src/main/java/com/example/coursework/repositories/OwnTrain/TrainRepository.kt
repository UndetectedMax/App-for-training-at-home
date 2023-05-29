package com.example.coursework.repositories.OwnTrain

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TrainRepository {
    private val database = Firebase.database
    private val userDBRef = database.getReference("trainings")
    fun createOrUpdateTrain(train: OwnTrainInfo) {
        val userNodeRef = userDBRef.child(train.trainCode)
        userNodeRef.setValue(train)
    }

    fun deleteTrain(train:OwnTrainInfo) {
        val trainNodeRef = userDBRef.child(train.trainCode)
        trainNodeRef.removeValue()
    }


}