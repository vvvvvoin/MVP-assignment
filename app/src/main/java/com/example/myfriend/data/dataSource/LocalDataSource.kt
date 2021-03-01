package com.example.myfriend.data.dataSource

import com.example.myfriend.data.db.MyDataBase
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Nation
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LocalDataSource(
    private val myDataBase: MyDataBase
) {
    private val myDao = myDataBase.myDataBaseDao()
    //private val list = myDao.getFriendList()

//    fun getFriendList(): List<Friend> {
//        return list
//    }

    fun getFriendList() : Single<List<Friend>> {
        return myDao.getFriendList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFriendListSeq() : Single<List<Friend>> {
        return myDao.getFriendListSeq()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addFriend(friend: Friend): Single<Long> {
        return myDao.insertFriend(friend)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun updateFriend(friend: Friend): Single<Int> {
        return myDao.updateFriend(friend)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFavorite(nation : String): Single<List<Nation>> {
        return myDao.getFavorite(nation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun setFavorite(nation: Nation): Single<Long> {
        return myDao.setFavorite(nation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deFavorite(nation: Nation): Single<Int> {
        return myDao.deFavorite(nation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}