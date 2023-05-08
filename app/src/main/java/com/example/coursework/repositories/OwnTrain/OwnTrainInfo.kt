package com.example.coursework.repositories.OwnTrain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OwnTrainInfo(
    var trainCode:String = "",
    var trainAuthor:String = "",
    var trainName:String = "",
    var trainDescription:String = "" ) : Parcelable
