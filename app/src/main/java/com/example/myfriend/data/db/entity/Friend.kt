package com.example.myfriend.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "friend")
@Parcelize
data class Friend(
    @PrimaryKey(autoGenerate = true)
    var seq : Int = 0,
    var id: String,
    var name: String,
    var number: String?,
    var email: String?,
    var flagUri: String,
    var nation : String,
    var profile : String?
)  : Parcelable {

    constructor(name: String, number: String?, email: String?, flagUri: String, nation: String, profile : String?) : this(
        0, UUID.randomUUID().toString(), name = name, number = number, email = email, flagUri = flagUri, nation = nation, profile = profile
    )

}