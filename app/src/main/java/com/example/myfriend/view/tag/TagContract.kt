package com.example.myfriend.view.tag

import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.dataSource.remoteData.NationW
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.view.home.ListOrderType

interface TagContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun showTagDetail()
        fun errorMessage(error : String)
    }

    interface Presenter {
        fun setView(view: View)
        fun detachView()
        fun openTagDetail(tagList : List<Tag>)
        fun searchTag(query: String)
        fun deleteTag(tagList: ArrayList<Tag>)
        fun setOrder(orderType: ListOrderType)
    }
}