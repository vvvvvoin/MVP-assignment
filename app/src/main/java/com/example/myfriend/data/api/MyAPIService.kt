package com.example.myfriend.data.api


import com.example.myfriend.model.vo.Nation
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface MyAPIService {
    @GET("/rest/v2/name/{name}")
    fun searchName(@Path("name") name : String): Single<ArrayList<Nation>>

    @GET("/rest/v2/alpha/{code}")
    fun searchCode(@Path("code") code : String): Single<Nation>

    @GET("/rest/v2/callingcode/{callingCode}")
    fun searchCallingCode(@Path("callingCode") callingCode : Int): Single<ArrayList<Nation>>

}