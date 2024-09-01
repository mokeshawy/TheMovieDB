package com.paymob.themoviedb.feature.fragments.comoun.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("originalLanguage")
    val originalLanguage: String,
    @ColumnInfo("originalTitle")
    val originalTitle: String,
    @ColumnInfo("overview")
    val overview: String,
    @ColumnInfo("posterPath")
    val posterPath: String? = null,
    @ColumnInfo("releaseDate")
    val releaseDate: String,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("voteAverage")
    val voteAverage: Double,
    @ColumnInfo("voteCount")
    val voteCount: Int,
    @ColumnInfo("isFavorite")
    var isFavorite: Boolean = false,
)