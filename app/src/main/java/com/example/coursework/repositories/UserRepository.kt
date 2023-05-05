package com.example.coursework.repositories

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class UserRepository {

    fun createOrUpdateUser(user: UserInfo) {
        val database = Firebase.database
        val userDBRef = database.getReference("users")
        val userNodeRef = userDBRef.child(user.uid)
        userNodeRef.setValue(user)
    }
    fun getSportsmen() = FirebaseDatabase.getInstance().getReference("users")
}

fun mapFromFirebaseUser(user: FirebaseUser):UserInfo = UserInfo(user.uid,user.displayName ?: "Anonymous user",user.photoUrl.toString())