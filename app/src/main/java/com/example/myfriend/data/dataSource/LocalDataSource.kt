package com.example.myfriend.data.dataSource

import com.example.myfriend.data.db.MyDataBase

class LocalDataSource(
    private val myDataBase: MyDataBase
) {
    private val myDao = myDataBase.myDataBaseDao()
}