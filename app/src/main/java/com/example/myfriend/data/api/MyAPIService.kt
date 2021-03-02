package com.example.myfriend.data.api


import com.example.myfriend.data.dataSource.remoteData.NationW
import io.reactivex.Single
import retrofit2.http.*

interface MyAPIService {
    @GET("/rest/v2/name/{name}")
    fun searchName(@Path("name") name : String): Single<ArrayList<NationW>>

    @GET("/rest/v2/alpha/{code}")
    fun searchCode(@Path("code") code : String): Single<NationW>

    @GET("/rest/v2/callingcode/{callingCode}")
    fun searchCallingCode(@Path("callingCode") callingCode : Int): Single<ArrayList<NationW>>

}