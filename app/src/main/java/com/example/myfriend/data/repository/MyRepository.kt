package com.example.myfriend.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myfriend.data.dataSource.LocalDataSource
import com.example.myfriend.data.dataSource.RemoteDataSource
import com.example.myfriend.model.vo.Nation
import java.util.regex.Pattern

class MyRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    private val TAG = "MyRepository"
    private val nationListResult = MutableLiveData<ArrayList<Nation>>()
    fun nationListResultObserve() = nationListResult

    private var lastSearchQuery : String = ""

    fun searchNation(query: String) {
        if(query != lastSearchQuery){
            lastSearchQuery = query
            if (Pattern.matches("^[0-9]+$", query)) {
                remoteDataSource.searchCallingCode(Integer.valueOf(query)).subscribe({
                    Log.d(TAG, "숫자로 검색함")
                    nationListResult.value = it
                }, {

                })
            } else if (Pattern.matches("^[a-zA-Z]{2,3}$", query)) {
                remoteDataSource.searchCode(query).subscribe({
                    Log.d(TAG, "2,3문자로 검색함")
                    nationListResult.value = arrayListOf(it)
                }, {

                })
            } else if (Pattern.matches("^[a-zA-Z]+$", query)) {
                remoteDataSource.searchName(query).subscribe({
                    Log.d(TAG, "쌩으로 검색함")
                    nationListResult.value = it
                }, {

                })
            }
        } else {
            Log.d(TAG, "이전 입력과 같음")
        }
    }
}