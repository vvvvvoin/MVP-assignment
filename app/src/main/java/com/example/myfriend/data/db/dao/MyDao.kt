package com.example.myfriend.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Nation
import io.reactivex.Single


@Dao
interface MyDao {

    //nationFavorite
    @Query("SELECT * FROM nation WHERE nation = :name")
    fun getFavorite(name : String) : Single<Nation>

    @Insert
    fun setFavorite(nation : Nation) : Single<Long>

    @Delete
    fun deFavorite(nation : Nation ) : Single<Int>


    //friend
    @Insert
    fun insertFriend(friend : Friend) : Single<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFriend(friend : Friend) : Single<Int>

    @Query("SELECT * FROM friend ORDER BY name")
    fun getFriendList() : Single<List<Friend>>



/*    @Query("SELECT * FROM friend ORDER BY name")
    fun getFriendListObservable() :  LiveData<List<Friend>>

    @Query("SELECT * FROM friend ORDER BY seq")
    fun getFriendListOrderBySeq() : List<Friend>

    @Query("SELECT * FROM friend ORDER BY seq")
    fun getFriendListOrderBySeqObservable() : LiveData<List<Friend>>*/
}