package com.example.myfriend.data.repository

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

abstract class BaseRepository  {
    protected val compositeDisposable = CompositeDisposable()

    operator fun invoke(disposable: Disposable) {
        disposable.addTo(compositeDisposable)
    }

}
