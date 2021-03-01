package com.example.myfriend.view.home.addEdit

import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.repository.MyRepository

class AddEditPresenter(private val myRepository: MyRepository, private val mId : String? = null) : AddEditContract.Presenter {
    private val TAG = "AddEditPresenter"

    private lateinit var view : AddEditContract.View


    init {

    }

    override fun setView(view: AddEditContract.View) {
        this.view = view
        this.view.setPresenter(this)
    }

    override fun addEdit(name: String, number: String?, email: String?, flag: String, nation : String, profile : String?) {
        if(mId == null){
            myRepository.addFriend(Friend(name, number, email, flag, nation, profile))
        }else{
            myRepository.updateFriend(Friend(mId, name, number, email, flag, nation, profile))
        }
        view.completeAddEdit()
    }


}