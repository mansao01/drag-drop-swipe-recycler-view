package com.example.dragablerecyclerview

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sports(
    var title: String?,
    var info: String?,
    var image: Int?,
    var news: String?
) : Parcelable