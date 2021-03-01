package com.example.myfriend.view.home

import android.content.Intent
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.di.repository
import com.example.myfriend.model.vo.Nation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

}