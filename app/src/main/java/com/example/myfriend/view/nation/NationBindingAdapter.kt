package com.example.myfriend.view.nation

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfriend.data.dataSource.remoteData.NationW

@BindingAdapter("nationItemList")
fun setNationItemList(
    recyclerView: RecyclerView,
    item: ArrayList<NationW>?
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