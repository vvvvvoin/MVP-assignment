package com.example.myfriend.view.home

interface HomeContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun errorMessage(error : String)

    }

    interface Presenter {
        fun setView(view: View)
        fun setOrder(listOrderType : ListOrderType)
    }
}