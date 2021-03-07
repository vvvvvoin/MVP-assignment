package com.example.myfriend.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "nation")
@Parcelize
data class Nation(
    @PrimaryKey
    var alpha2Code : String,
    var nation : String
) : Parcelable