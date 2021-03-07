package com.example.myfriend.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Tag(
    @PrimaryKey(autoGenerate = true)
    var seq: Int = 0,
    var tagName: String,
    @ForeignKey(
        entity = Friend::class,
        parentColumns = ["id"],
        childColumns = ["friendId"],
        onDelete = ForeignKey.CASCADE
    ) var friendId: String,
    @Ignore var isCheck : Boolean
) : Parcelable {
    constructor(tagName: String) : this(0, tagName = tagName, friendId = "", isCheck = false)
}