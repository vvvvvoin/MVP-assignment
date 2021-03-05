package com.example.myfriend.view.home.detail

import com.example.myfriend.data.db.entity.Friend


interface DetailContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun errorMessage(error : String)
        fun openNumberApp(number : String)
        fun openEmailApp(toEmail : String)
    }

    interface Presenter {
        fun setView(view: View)
        fun detachView()
    }
}