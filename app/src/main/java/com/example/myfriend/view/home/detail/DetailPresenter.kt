package com.example.myfriend.view.home.detail

import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.repository.MyRepository

class DetailPresenter(private val myRepository: MyRepository) : DetailContract.Presenter {
    private val TAG = "DetailPresenter"

    private lateinit var view : DetailContract.View


    init {

    }

    override fun setView(view: DetailContract.View) {
        this.view = view
        this.view.setPresenter(this)
    }


}