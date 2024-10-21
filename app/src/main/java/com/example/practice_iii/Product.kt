package com.example.practice_iii

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int = 0,
    val name: String,
    val description: String,
    val seller: String,
    val price: Double,
    val picture: String // Could be a URL or a resource identifier
) : Parcelable
