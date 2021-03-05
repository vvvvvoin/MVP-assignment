package com.example.myfriend.view.nation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.repository.MyRepository

class NationDetailPresenter(private val myRepository: MyRepository) : NationDetailContract.Presenter {
    private val TAG = "NationPresenter"

    private var view : NationDetailContract.View? = null


    init {


    }

    override fun setView(view: NationDetailContract.View) {
        this.view = view
        this.view!!.setPresenter(this)
    }

    override fun detachView() {
        view = null
    }

    override fun setFavorite(nation: Nation, check : Boolean) {
        myRepository.setFavorite(nation, check)
    }
}