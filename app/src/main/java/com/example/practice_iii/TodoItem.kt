package com.example.practice_iii

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoItem(
    var id: Int = 0,
    var title: String,
    var description: String
) : Parcelable
