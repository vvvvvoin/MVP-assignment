package com.example.myfriend.di

import androidx.room.Room
import com.example.myfriend.data.api.MyAPIService
import com.example.myfriend.data.dataSource.LocalDataSource
import com.example.myfriend.data.dataSource.RemoteDataSource
import com.example.myfriend.data.db.MyDataBase
import com.example.myfriend.data.repository.MyRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT: Long = 15
private const val WRITE_TIMEOUT: Long = 15
private const val READ_TIMEOUT: Long = 15

val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder().apply {
    addInterceptor(httpLoggingInterceptor)
    connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
    writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
}.build()

val retrofit: Retrofit = Retrofit
    .Builder()
    .baseUrl("https://restcountries.eu")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

private val myApi: MyAPIService = retrofit.create(MyAPIService::class.java)

val dbPart = module {
    single { Room.databaseBuilder(androidApplication(), MyDataBase::class.java, "myData").build() }
}

val retrofitPart = module {
    single{ myApi }
}

val dataSourceModule = module {
    factory { RemoteDataSource(get()) }
    factory { LocalDataSource(get())  }
}

val repository = module {
    single { MyRepository(get(), get()) }
}



var myDiModule = listOf(dbPart, retrofitPart, dataSourceModule, repository)