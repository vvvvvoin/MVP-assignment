package com.example.myfriend.view

interface MyContract {
    interface View {

    }

    interface Presenter {
        fun setView(view: View?)
    }
}