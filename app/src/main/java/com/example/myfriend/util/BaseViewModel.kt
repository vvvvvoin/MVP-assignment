package com.example.myfriend.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    protected val _error = MediatorLiveData<Event<String>>()
    val error: LiveData<Event<String>>
        get() = _error

    private val disposable = CompositeDisposable()

    operator fun invoke(disposable: Disposable) {
        this.disposable.add(disposable)
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}