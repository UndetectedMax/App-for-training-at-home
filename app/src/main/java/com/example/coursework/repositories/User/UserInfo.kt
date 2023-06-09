package com.example.coursework.repositories.User

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val uid: String = "",
    val displayName: String = "",
    var photo: String? = ""
) : Parcelable