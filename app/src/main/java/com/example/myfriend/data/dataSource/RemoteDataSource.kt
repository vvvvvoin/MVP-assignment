package com.example.myfriend.data.dataSource

import com.example.myfriend.data.api.MyAPIService
import com.example.myfriend.model.vo.Nation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Response

class RemoteDataSource(
    private val myAPIService: MyAPIService
) {
    fun searchName(name : String): Single<ArrayList<Nation>> {
        return myAPIService.searchName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun searchCallingCode(callingCode : Int): Single<ArrayList<Nation>> {
        return myAPIService.searchCallingCode(callingCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun searchCode(code : String): Single<Nation> {
        return myAPIService.searchCode(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}