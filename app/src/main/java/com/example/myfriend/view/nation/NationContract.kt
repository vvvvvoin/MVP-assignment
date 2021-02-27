package com.example.myfriend.view.nation

interface NationContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun showNationDetail(data : String, check : Boolean)
        fun errorMessage(error : String)
    }

    interface Presenter {
        fun setView(view: View)
        fun openNationDetail(query : String)
        fun searchNation(query: String)
    }
}