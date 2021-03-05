package com.example.myfriend.view.tag

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.data.dataSource.remoteData.NationW
import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.view.home.ListOrderType

class TagPresenter(private val myRepository: MyRepository) : TagContract.Presenter {
    private val TAG = "NationPresenter"

    private var view: TagContract.View? = null
    private val resultTagList = myRepository.tagListWithQueryObserve()

    //private val _searchTag = MediatorLiveData<List<Tag>>()
    val searchTag: LiveData<List<Tag>>
        get() = resultTagList

    private var listOrderType  = ListOrderType.NAME
    var query = ""

    init {

    }

    override fun setView(view: TagContract.View) {
        this.view = view
        this.view!!.setPresenter(this)
    }

    override fun detachView() {
        view = null
        //_searchTag.removeSource(resultTagList)
    }

    override fun openTagDetail(tagList: List<Tag>) {

    }

    override fun searchTag(query: String) {
        this.query = query
        if(this.query.isNotEmpty())
            myRepository.searchTagListWithQuery(query, listOrderType)
    }

    override fun deleteTag(tagList: ArrayList<Tag>) {
        for(tag in tagList){
            if(tag.isCheck)
                myRepository.deleteTagInTagTab(tag.tagName)
        }
    }

    override fun setOrder(orderType: ListOrderType) {
        listOrderType = orderType
        myRepository.searchTagListWithQuery(query, listOrderType)
    }


}