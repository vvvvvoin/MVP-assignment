package com.example.myfriend.data.dataSource

import com.example.myfriend.data.api.MyAPIService
import com.example.myfriend.data.dataSource.remoteData.NationW
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RemoteDataSource(
    private val myAPIService: MyAPIService
) {
    fun searchName(name : String): Single<ArrayList<NationW>> {
        return myAPIService.searchName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun searchCallingCode(callingCode : Int): Single<ArrayList<NationW>> {
        return myAPIService.searchCallingCode(callingCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun searchCode(code : String): Single<NationW> {
        return myAPIService.searchCode(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}