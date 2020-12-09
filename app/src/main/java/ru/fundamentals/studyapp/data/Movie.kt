package ru.fundamentals.studyapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.fundamentals.studyapp.data.Actor

@Parcelize
data class Movie(
    val preview: Int,
    val ageRate: String,
    var isLike: Boolean = false,
    val genre: String,
    val countReviews: String,
    val rating: Int,
    val title: String,
    val duration: String,
    val description: String,
    val actors: List<Actor> = emptyList()
) : Parcelable