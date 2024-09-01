package com.paymob.themoviedb.feature.fragments.comoun.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RemoteKey")
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo("nextPage")
    val nextPage: Int?,
    @ColumnInfo("lastUpdated")
    val lastUpdated: Long
)
