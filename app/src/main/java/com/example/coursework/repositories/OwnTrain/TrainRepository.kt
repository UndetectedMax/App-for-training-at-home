package com.example.coursework.repositories.OwnTrain

import com.google.firebase.firestore.FirebaseFirestore

class TrainRepository {
    fun getTrains() = FirebaseFirestore.getInstance().collection("trainings")
}