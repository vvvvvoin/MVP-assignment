package com.example.myfriend.model.vo

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class Friend(
    var name: String,
    var number: String = "",
    var email: String = "",
    var flag : String
)