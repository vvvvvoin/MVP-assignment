package com.example.myfriend.view.tag.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.data.repository.MyRepository

class TagDetailPresenter(private val myRepository: MyRepository, private val tagName : String) : TagDetailContract.Presenter {
    private val TAG = "TagDetailPresenter"

    private var view : TagDetailContract.View? = null



    private val _friendList = MediatorLiveData<List<Friend>>()
    val friendList: LiveData<List<Friend>>
        get() = _friendList

    init {
        //이걸로 쿼리문 만들고 찾기
        tagName

    }

    override fun setView(view: TagDetailContract.View) {
        this.view = view
        this.view!!.setPresenter(this)
    }

    override fun detachView() {
        view = null
        //_friendList.removeSource(resultTagList)
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