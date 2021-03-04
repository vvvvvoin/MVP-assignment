package com.example.myfriend.data.dataSource

import com.example.myfriend.data.db.MyDataBase
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.db.entity.Tag
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

    fun getFriendListWithQuery(query : String): Single<List<Friend>> {
        return myDao.getFriendListWithQuery(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFriendListWithQuerySeq(query : String): Single<List<Friend>> {
        return myDao.getFriendListWithQuerySeq(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addFriend(friend: Friend): Single<Long> {
        return myDao.insertFriend(friend)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateFriend(friend: Friend): Single<Int> {
        return myDao.updateFriend(friend.id, friend.name, friend.number, friend.email, friend.flagUri, friend.nation, friend.profile)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTagList(friendId: String): Single<List<Tag>> {
        return myDao.getTagList(friendId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTagListWithQuery(query : String): Single<List<Tag>> {
        return myDao.getTagListWithQuery(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTagListWithQuerySeq(query : String): Single<List<Tag>> {
        return myDao.getTagListWithQuerySeq(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertTag(tag : List<Tag>) : Single<List<Long>> {
        return myDao.insertTag(tag)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteTag(friendId : String): Single<Int> {
        return myDao.deleteTag(friendId)
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