package com.example.coursework.repositories.OwnTrain

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TrainRepository {
    fun createOrUpdateTrain(train: OwnTrainInfo) {
        val database = Firebase.database
        val userDBRef = database.getReference("trainings")
        val userNodeRef = userDBRef.child(train.trainCode)
        userNodeRef.setValue(train)
    }

}