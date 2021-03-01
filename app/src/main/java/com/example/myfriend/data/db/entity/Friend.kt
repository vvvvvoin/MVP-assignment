package com.example.myfriend.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "friend")
data class Friend(
    @PrimaryKey
    var seq: String,
    var name: String,
    var number: String?,
    var email: String?,
    var flagUri: String,
    var nation : String,
    var profile : String?
) {

    constructor(name: String, number: String?, email: String?, flagUri: String, nation: String, profile : String?) : this(
        UUID.randomUUID().toString(), name = name, number = number, email = email, flagUri = flagUri, nation = nation, profile = profile
    )

}