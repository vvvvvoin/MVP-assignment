package com.example.myfriend.view.nation

import com.example.myfriend.model.vo.Nation

interface NationContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun showNationDetail(data : com.example.myfriend.data.db.entity.Nation, check : Boolean)
        fun errorMessage(error : String)
    }

    interface Presenter {
        fun setView(view: View)
        fun openNationDetail(nation : Nation)
        fun searchNation(query: String)
    }
}