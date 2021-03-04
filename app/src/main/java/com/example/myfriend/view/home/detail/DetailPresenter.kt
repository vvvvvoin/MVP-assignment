package com.example.myfriend.view.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.data.repository.MyRepository

class DetailPresenter(private val myRepository: MyRepository, private val friendId : String) : DetailContract.Presenter {
    private val TAG = "DetailPresenter"

    private lateinit var view : DetailContract.View

    private val resultTagList = myRepository.detailViewTagListObserve()

    private val _tagList = MediatorLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>>
        get() = _tagList

    init {
        myRepository.getTagList(friendId)

        _tagList.addSource(resultTagList){
            _tagList.value = it
        }

    }

    override fun setView(view: DetailContract.View) {
        this.view = view
        this.view.setPresenter(this)
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