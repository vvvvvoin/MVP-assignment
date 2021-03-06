package com.example.myfriend.view.tag.detail

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.view.home.addEdit.AddEditTagAdapter
import java.util.ArrayList

@BindingAdapter("setTagItemList")
fun setTagItemList(
    recyclerView: RecyclerView,
    item: List<Friend>?
) {
    val tagDetailAdapter : TagDetailAdapter

    if(recyclerView.adapter == null){
        return
    }else{
        tagDetailAdapter = recyclerView.adapter as TagDetailAdapter
    }
    item?.let {
        if(tagDetailAdapter.friendList == it) return
        tagDetailAdapter.friendList.clear()
        tagDetailAdapter.friendList =  ArrayList(it)
        tagDetailAdapter.notifyDataSetChanged()
    }
}