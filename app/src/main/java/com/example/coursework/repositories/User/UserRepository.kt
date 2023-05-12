 package com.example.coursework.repositories.User


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository {

    fun createOrUpdateUser(user: UserInfo) {
        val database = Firebase.database
        val userDBRef = database.getReference("users")
        val userNodeRef = userDBRef.child(user.uid)
        userNodeRef.setValue(user)
    }

    interface FetchSportsmanListener {
        fun onFetchSportsman(user: UserInfo)
    }

    fun getCurrentUserById(firebaseUser: FirebaseUser, listener: FetchSportsmanListener) {
        val database = Firebase.database
        val userDbRef = database.getReference("users")
        userDbRef.child(firebaseUser.uid).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    val user = mapFromFirebaseUser(FirebaseAuth.getInstance().currentUser!!)
                    listener.onFetchSportsman(user)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = mapFromFirebaseUser(FirebaseAuth.getInstance().currentUser!!)
                    listener.onFetchSportsman(user)
                }
            }
        )
    }
}

fun mapFromFirebaseUser(user: FirebaseUser): UserInfo =
    UserInfo(user.uid, user.displayName ?: "Anonymous user", user.photoUrl.toString())