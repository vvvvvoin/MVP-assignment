package com.example.myfriend.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myfriend.data.db.dao.MyDao
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.db.entity.Tag

@Database(entities = [(Nation::class), (Friend::class), (Tag::class)], version = 1)
abstract class MyDataBase : RoomDatabase(){
    abstract fun myDataBaseDao() : MyDao
}