package com.example.myfriend.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myfriend.data.dataSource.LocalDataSource
import com.example.myfriend.data.dataSource.RemoteDataSource
import com.example.myfriend.data.dataSource.remoteData.NationW
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.view.home.ListOrderType
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.function.Predicate
import java.util.regex.Pattern

@SuppressLint("CheckResult")
class MyRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : BaseRepository(){
    private val TAG = "MyRepository"
    //remote
    private val nationListResult = MutableLiveData<ArrayList<NationW>>()
    fun nationListResultObserve() = nationListResult

    private var lastSearchQuery : String = ""

    //잘 동작하는데 맞는 방법일까?
    fun initNationListResult() {
        nationListResult.value?.clear()
    }

    fun searchNation(query: String) {
        if(query != lastSearchQuery){
            lastSearchQuery = query
            if (Pattern.matches("^[0-9]+$", query)) {
                remoteDataSource.searchCallingCode(Integer.valueOf(query)).subscribe({
                    Log.d(TAG, "숫자로 검색함")
                    nationListResult.value = it
                }, {
                    Log.d(TAG, "숫자로 실패 ${it.toString()}")
                })
            } else if (Pattern.matches("^[a-zA-Z]{2,3}$", query)) {
                remoteDataSource.searchCode(query).subscribe({
                    Log.d(TAG, "2,3문자로 검색함")
                    nationListResult.value = arrayListOf(it)
                }, {
                    Log.d(TAG, "2,3문자로 검색함 ${it.toString()}")
                })
            } else if (Pattern.matches("^[a-zA-Z]+$", query)) {
                remoteDataSource.searchName(query).subscribe({
                    Log.d(TAG, "쌩으로 검색함")
                    nationListResult.value = it
                }, {
                    Log.d(TAG, "쌩으로 검색 실패 이유는 ${it.toString()}")
                })
            }
        } else {
            Log.d(TAG, "이전 입력과 같음")
        }
    }


    //localData
    private val friendListResult = MutableLiveData<List<Friend>>()
    fun friendListResultObserve() = friendListResult

    //friend
    fun getFriendList(listOrderType: ListOrderType) {
        when (listOrderType) {
            ListOrderType.NAME -> {
                localDataSource.getFriendList().subscribe({
                    friendListResult.value = it
                }, {
                    Log.d(TAG, "친구 찾기 실패 $it")
                })
            }
            ListOrderType.SEQ -> {
                localDataSource.getFriendListSeq().subscribe({
                    friendListResult.value = it
                }, {
                    Log.d(TAG, "친구 찾기 실패 $it")
                })
            }
        }
    }

    fun getFriendListWithQuery(query: String, listOrderType: ListOrderType){
        val wildQuery = "%$query%"
        when (listOrderType) {
            ListOrderType.NAME -> {
                localDataSource.getFriendListWithQuery(wildQuery).subscribe({
                    friendListResult.value = it
                }, {
                    Log.d(TAG, "친구 찾기 실패 $it")
                })
            }
            ListOrderType.SEQ -> {
                localDataSource.getFriendListWithQuerySeq(wildQuery).subscribe({
                    friendListResult.value = it
                }, {
                    Log.d(TAG, "친구 찾기 실패 $it")
                })
            }
        }
    }

    fun addFriend(friend: Friend, tagList: List<Tag>?){
        localDataSource.addFriend(friend).subscribe({
            Log.d(TAG, "친구 추가 성공 $it")
            if (tagList != null) insertTag(tagList)
        }, {
            Log.d(TAG, "친구 추가 실패 ${it}")
        })
    }

    fun updateFriend(friend: Friend){
        localDataSource.updateFriend(friend).subscribe({
            Log.d(TAG, "친구 업데이트 성공 $it")
        }, {
            Log.d(TAG, "친구 업데이트 실패")
        })
    }

    // Tag
    private val tagListResult = MutableLiveData<List<Tag>>()
    fun tagListResultObserve() = tagListResult

    private val detailViewTagListResult = MutableLiveData<List<Tag>>()
    fun detailViewTagListObserve() = detailViewTagListResult

    private val tagListWithQueryResult = MutableLiveData<List<Tag>>()
    fun tagListWithQueryObserve() = tagListWithQueryResult

    fun getTagList(friendId: String){
        localDataSource.getTagList(friendId).subscribe({
            detailViewTagListResult.value = it.distinct()
            Log.d(TAG, "상세 뷰 태그 찾기 성공 $it")
        }, {
            Log.d(TAG, "상세 뷰 태그 실패 성공 $it")
        })
    }

    fun searchTagListWithQuery(query: String, listOrderType: ListOrderType){
        val wildQuery = "%$query%"
        when (listOrderType) {
            ListOrderType.NAME -> {
                localDataSource.getTagListWithQuery(wildQuery).subscribe({
                    tagListWithQueryResult.value = it.distinctBy { it.tagName }
                    Log.d(TAG, "태그 찾기 성공 $it")
                }, {
                    Log.d(TAG, "태그 찾기 실패 $it")
                })
            }
            ListOrderType.SEQ -> {
                localDataSource.getTagListWithQuerySeq(wildQuery).subscribe({
                    tagListWithQueryResult.value =  it.distinctBy { it.tagName }
                    Log.d(TAG, "태그 찾기 성공 $it")
                }, {
                    Log.d(TAG, "태그 찾기 실패 $it")
                })
            }
        }
    }

    private fun insertTag(tagList: List<Tag>){
        localDataSource.insertTag(tagList).subscribe({
            Log.d(TAG, "태그 추가 성공 $it")
        }, {
            Log.d(TAG, "태그 추가 실패 ${it}")
        })
    }

    fun deleteTagInTagTab(tag: String){
        localDataSource.deleteTagInTagTab(tag).subscribe({
            Log.d(TAG, "태그 삭제 성공 $it")
        }, {
            Log.d(TAG, "태그 삭제 실패 ${it}")
        })
    }

    fun deleteTag(friendId: String, tagList: List<Tag>?){
        localDataSource.deleteTag(friendId).subscribe({
            Log.d(TAG, "태그 삭제 성공 $it")
            if (tagList != null) insertTag(tagList)
        }, {
            Log.d(TAG, "태그 삭제 실패 ${it}")
        })
    }

    //nation
    private var favoriteNationResult  = MutableLiveData<Nation>()
    fun favoriteNationResultObserve() = favoriteNationResult

    fun getFavorite(nation: String) {
        localDataSource.getFavorite(nation).subscribe({
            if (it.isEmpty()) {
                favoriteNationResult.value =
                    Nation("none", "none")
            } else {
                favoriteNationResult.value = it[0]
            }
        }, {
            Log.d(TAG, "국가 즐겨찾기 불러오기 실패 ${it}")
        })
    }

    fun setFavorite(nation: Nation, check: Boolean){
        if(check == true){
            localDataSource.setFavorite(nation).subscribe({
                Log.d(TAG, "국가 즐겨찾기 삽입 성공 $it")
            }, {
                Log.d(TAG, "국가 즐겨찾기 삽입 실패 $it")
            })
        }else{
            localDataSource.deFavorite(nation).subscribe({
                Log.d(TAG, "국가 즐겨찾기 삭제 성공 $it")
            }, {
                Log.d(TAG, "국가 즐겨찾기 삭제 실패 $it")
            })
        }
    }


    fun clear(){
        compositeDisposable.clear()
    }
}