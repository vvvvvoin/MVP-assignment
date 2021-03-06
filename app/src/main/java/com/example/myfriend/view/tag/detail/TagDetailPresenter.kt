package com.example.myfriend.view.tag.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.data.repository.MyRepository

class TagDetailPresenter(private val myRepository: MyRepository, private val tagName : String) : TagDetailContract.Presenter {
    private val TAG = "TagDetailPresenter"

    private var view : TagDetailContract.View? = null

    private val resultFriendList = myRepository.friendListWithTagNameObserve()

    private val _friendList = MediatorLiveData<List<Friend>>()
    val friendList: LiveData<List<Friend>>
        get() = _friendList

    init {
        myRepository.getFriendListWithTagName(tagName)
        _friendList.addSource(resultFriendList){
            _friendList.value = it
        }
    }

    override fun setView(view: TagDetailContract.View) {
        this.view = view
        this.view!!.setPresenter(this)
    }

    override fun detachView() {
        view = null
        _friendList.removeSource(resultFriendList)
    }

    fun openNumberApp(number : String?){
        if(number != null)
            view?.openNumberApp(number)
    }

    fun openEmailApp(email : String?){
        if(email != null)
            view?.openEmailApp(email)
    }

}