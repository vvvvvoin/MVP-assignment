package com.example.myfriend.view.nation

import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.dataSource.remoteData.NationW

interface NationContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun showNationDetail(data : Nation, check : Boolean)
        fun errorMessage(error : String)
    }

    interface Presenter {
        fun setView(view: View)
        fun detachView()
        fun openNationDetail(nationW : NationW)
        fun searchNation(query: String)
    }
}