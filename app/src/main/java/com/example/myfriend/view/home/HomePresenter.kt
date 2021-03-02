package com.example.myfriend.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.repository.MyRepository

class HomePresenter(private val myRepository: MyRepository) : HomeContract.Presenter {
    private val TAG = "HomePresenter"

    private lateinit var view : HomeContract.View
    private val resultNationList = myRepository.friendListResultObserve()

    private val _friendList = MediatorLiveData<List<Friend>>()
    val friendList: LiveData<List<Friend>>
        get() = _friendList

    init {
        myRepository.getFriendList(ListOrderType.NAME)
        _friendList.addSource(resultNationList){
            Log.d(TAG, it.toString())
            _friendList.value = it
        }

    }

    override fun setView(view: HomeContract.View) {
        this.view = view
        this.view.setPresenter(this)
    }

    override fun setOrder(listOrderType: ListOrderType) {
        myRepository.getFriendList(listOrderType)
    }

    fun openNumberApp(number : String?){
        if(number != null)
            view.openNumberApp(number)
    }

    fun openEmailApp(email : String?){
        if(email != null)
            view.openEmailApp(email)
    }

}