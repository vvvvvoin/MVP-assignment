package com.example.myfriend.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myfriend.util.Event
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

abstract class BaseRepository  {
    protected val _error = MediatorLiveData<Event<String>>()
    val error: LiveData<Event<String>>
        get() = _error


    protected val compositeDisposable = CompositeDisposable()

    operator fun invoke(disposable: Disposable) {
        disposable.addTo(compositeDisposable)
    }

}
