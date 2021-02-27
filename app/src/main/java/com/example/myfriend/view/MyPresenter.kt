package com.example.myfriend.view

class MyPresenter : MyContract.Presenter {
    private var view : MyContract.View? = null

    override fun setView(view: MyContract.View?) {
        this.view = view
    }
}