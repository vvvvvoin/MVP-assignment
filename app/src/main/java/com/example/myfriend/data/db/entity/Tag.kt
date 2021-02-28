package com.example.myfriend.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Tag(
    @PrimaryKey
    var seq: Int,
    var tagName: String,
    @ForeignKey(
        entity = Friend::class,
        parentColumns = ["seq"],
        childColumns = ["friendSeq"],
        onDelete = ForeignKey.CASCADE
    ) var friendSeq: Int
)