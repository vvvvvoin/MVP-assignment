package com.example.myfriend.view.nation.detail

import com.example.myfriend.data.db.entity.Nation


interface NationDetailContract {
    interface View {
        fun setPresenter(presenter: Presenter)
    }

    interface Presenter {
        fun setView(view: View)
        fun detachView()
        fun setFavorite(nation : Nation, check : Boolean)
    }
}