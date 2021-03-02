package com.example.myfriend.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Nation
import io.reactivex.Single


@Dao
interface MyDao {

    //nationFavorite
    @Query("SELECT * FROM nation WHERE alpha2Code = :name")
    fun getFavorite(name : String) : Single<List<Nation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setFavorite(nation : Nation) : Single<Long>

    @Delete
    fun deFavorite(nation : Nation ) : Single<Int>


    //friend
    @Insert
    fun insertFriend(friend : Friend) : Single<Long>

    @Query("UPDATE friend SET name = :name, number = :number, email = :email, flagUri = :flagUri, nation = :nation, profile = :profile WHERE id = :id")
    fun updateFriend(
        id: String,
        name : String,
        number: String? = "",
        email: String? = "",
        flagUri: String,
        nation: String,
        profile: String? = ""
    ): Single<Int>

    @Query("SELECT * FROM friend ORDER BY name")
    fun getFriendList() : Single<List<Friend>>

    @Query("SELECT * FROM friend ORDER BY seq")
    fun getFriendListSeq() : Single<List<Friend>>

/*    @Query("SELECT * FROM friend ORDER BY name")
    fun getFriendListObservable() :  LiveData<List<Friend>>

    @Query("SELECT * FROM friend ORDER BY seq")
    fun getFriendListOrderBySeq() : List<Friend>

    @Query("SELECT * FROM friend ORDER BY seq")
    fun getFriendListOrderBySeqObservable() : LiveData<List<Friend>>*/
}