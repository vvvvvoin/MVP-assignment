package com.example.myfriend.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag(
    @PrimaryKey
    val seq: Int
)