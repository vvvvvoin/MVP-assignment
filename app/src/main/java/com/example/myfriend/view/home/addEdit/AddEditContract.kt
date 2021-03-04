package com.example.myfriend.view.home.addEdit

import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Tag


interface AddEditContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun errorMessage(error : String)
        fun completeAddEdit()
    }

    interface Presenter {
        fun setView(view: View)
        fun addEdit(name: String, number: String?, email: String?, flag: String, nation : String, profile : String?, tagList : ArrayList<Tag>)
    }
}