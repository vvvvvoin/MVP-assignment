package com.example.myfriend.view.tag

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.view.home.addEdit.AddEditTagAdapter
import java.util.ArrayList

@BindingAdapter("tagItemList")
fun setTagItemList(
    recyclerView: RecyclerView,
    item: List<Tag>?
) {
    val tagAdapter : TagAdapter

    if(recyclerView.adapter == null){
        return
    }else{
        tagAdapter = recyclerView.adapter as TagAdapter
    }
    item?.let {
        if(tagAdapter.tagList == it) return
        tagAdapter.tagList.clear()
        tagAdapter.tagList =  it as ArrayList<Tag>
        tagAdapter.notifyDataSetChanged()
    }
}