package com.example.task18

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MsgItem(
    var body: String,
    val id: Int,
    var title: String,
    val userId: Int
) : Parcelable