package com.example.coursework.repositories.OwnTrain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OwnTrainInfo(
    val trainCode:String = "",
    val trainAuthor:String = "",
    val trainName:String = "",
    val trainDescription:String = "" ) : Parcelable
