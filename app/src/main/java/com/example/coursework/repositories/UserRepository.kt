package com.example.coursework.repositories

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class UserRepository {

    fun createOrUpdateUser(user: UserInfo) {
        val database = Firebase.database
        val userdatabaseRef = database.getReference("users")
        val userNodeRef = userdatabaseRef.child(user.userId)
        userNodeRef.setValue(user)
    }
}
fun mapFromFirebaseUser(user: FirebaseUser):UserInfo = UserInfo(user.uid,user.displayName ?: "Anonymous user",user.photoUrl.toString())