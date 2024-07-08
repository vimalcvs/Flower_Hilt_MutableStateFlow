package com.example.flower.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ModelFlower(
    val description: String, val imageUrl: String, val name: String
) : Parcelable