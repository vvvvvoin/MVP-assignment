package com.example.myfriend.view.tag

import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.view.home.ListOrderType

interface TagContract {
    interface View {
        fun setPresenter(presenter: Presenter)
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