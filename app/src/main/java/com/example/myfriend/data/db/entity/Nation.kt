package com.example.myfriend.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nation")
data class Nation(
    @PrimaryKey
    var nation : String,
    var flagUri : String
)