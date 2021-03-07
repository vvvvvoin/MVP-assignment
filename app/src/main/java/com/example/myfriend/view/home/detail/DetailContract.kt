package com.example.myfriend.view.home.detail

interface DetailContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun openNumberApp(number : String)
        fun openEmailApp(toEmail : String)
    }

    interface Presenter {
        fun setView(view: View)
        fun detachView()
    }
}