package com.example.myfriend.view.nation

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfriend.model.vo.Nation

@BindingAdapter("item_list")
fun setBookList(
    recyclerView: RecyclerView,
    item: ArrayList<Nation>?
) {
    val searchAdapter :NationAdapter

    if(recyclerView.adapter == null){
        return
    }else{
        searchAdapter = recyclerView.adapter as NationAdapter
    }
    item?.let {
        if(searchAdapter.nationList == it) return
        searchAdapter.nationList.clear()
        searchAdapter.nationList =  it
        searchAdapter.notifyDataSetChanged()
    }
}