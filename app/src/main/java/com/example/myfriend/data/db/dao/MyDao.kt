package com.example.myfriend.data.db.dao

import androidx.room.*
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.db.entity.Tag
import io.reactivex.Single


@Dao
interface MyDao {

    //nationFavorite
    @Query("SELECT * " +
            "FROM nation " +
            "WHERE alpha2Code = :name")
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

    //order by 에 값은 명시적으로 입력해야됨 파라미터값으로 왜 안됨?
    @Query("SELECT * FROM (" +
            "SELECT * FROM friend WHERE name LIKE :query " +
            "UNION " +
            "SELECT * FROM friend WHERE number LIKE :query " +
            "UNION " +
            "SELECT * FROM friend WHERE email LIKE :query) " +
            "ORDER BY NAME")
    fun getFriendListWithQuery(query : String) : Single<List<Friend>>

    @Query("SELECT * FROM (" +
            "SELECT * FROM friend WHERE name LIKE :query " +
            "UNION " +
            "SELECT * FROM friend WHERE number LIKE :query " +
            "UNION " +
            "SELECT * FROM friend WHERE email LIKE :query) " +
            "ORDER BY SEQ")
    fun getFriendListWithQuerySeq(query : String) : Single<List<Friend>>


    //tag
    @Query("SELECT DISTINCT * " +
            "FROM tag " +
            "WHERE tagName LIKE :query " +
            "ORDER BY tagName")
    fun getTagListWithQuery(query : String) : Single<List<Tag>>

    @Query("SELECT DISTINCT * " +
            "FROM tag " +
            "WHERE tagName LIKE :query " +
            "ORDER BY seq")
    fun getTagListWithQuerySeq(query : String) : Single<List<Tag>>

    @Query("SELECT * FROM Tag WHERE friendId = :friendId")
    fun getTagList(friendId: String) : Single<List<Tag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTag(tag : List<Tag>): Single<List<Long>>

    @Query("DELETE  FROM Tag WHERE friendId = :friendId")
    fun deleteTag(friendId : String): Single<Int>

    @Query("DELETE FROM TAG WHERE tagName = :tagName")
    fun deleteTagInTagTab(tagName : String) : Single<Int>
}