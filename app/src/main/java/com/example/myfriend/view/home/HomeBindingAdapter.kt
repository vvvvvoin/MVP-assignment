package com.example.myfriend.view.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfriend.data.db.entity.Friend

@BindingAdapter("homeItemList")
fun setHomeItemList(
    recyclerView: RecyclerView,
    item: List<Friend>?
) {
    val searchAdapter : HomeAdapter

    if(recyclerView.adapter == null){
        return
    }else{
        searchAdapter = recyclerView.adapter as HomeAdapter
    }
    item?.let {
        if(searchAdapter.friendList == it) return
        searchAdapter.friendList.clear()
        searchAdapter.friendList =  it as ArrayList<Friend>
        searchAdapter.notifyDataSetChanged()
    }
}