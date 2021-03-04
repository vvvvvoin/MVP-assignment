package com.example.myfriend.view.home.addEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Tag
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

    override fun addEdit(name: String, number: String?, email: String?, flag: String, nation : String, profile : String?, tagList : ArrayList<Tag>) {
        if(mId == null){
            val insertData = Friend(name, number, email, flag, nation, profile)
            val friendId = insertData.id
            if(tagList.isNotEmpty()) tagList.forEach { it.friendId = friendId }
            myRepository.addFriend(insertData, tagList)
        }else{
            myRepository.updateFriend(Friend(0, mId, name, number, email, flag, nation, profile))
            if(tagList.isNotEmpty()) tagList.forEach { it.friendId = mId }
            myRepository.deleteTag(mId, tagList)
        }
        view.completeAddEdit()
    }


}