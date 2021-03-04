package com.example.myfriend.view.home

interface HomeContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun errorMessage(error : String)
        fun openNumberApp(number : String)
        fun openEmailApp(toEmail : String)
    }

    interface Presenter {
        fun setView(view: View)
        fun setOrder(orderType : ListOrderType)
        fun searchWithQuery(query : String)
    }
}