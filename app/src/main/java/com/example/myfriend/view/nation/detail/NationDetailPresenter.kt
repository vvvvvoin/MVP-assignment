package com.example.myfriend.view.nation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.repository.MyRepository

class NationDetailPresenter(private val myRepository: MyRepository) : NationDetailContract.Presenter {
    private val TAG = "NationPresenter"

    private lateinit var view : NationDetailContract.View


    init {


    }

    override fun setView(view: NationDetailContract.View) {
        this.view = view
        this.view.setPresenter(this)
    }

    override fun setFavorite(nation: Nation, check : Boolean) {
        myRepository.setFavorite(nation, check)
    }
}