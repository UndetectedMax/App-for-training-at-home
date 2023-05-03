package com.example.coursework.repositories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val userId: String = " ",
    val name: String = "",
    var photo: String = ""
): Parcelable