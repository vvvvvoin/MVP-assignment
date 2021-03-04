package com.example.myfriend.view.tag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.databinding.ItemTagAddEditBinding
import com.example.myfriend.databinding.ItemTagTabBinding

class TagAdapter : RecyclerView.Adapter<TagAdapter.ItemHolder>() {

    var tagList = ArrayList<Tag>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(ItemTagTabBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    inner class ItemHolder(private val binding: ItemTagTabBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var checkBox = binding.tagCheckBox
        fun bind(item: Tag) {
            binding.tag = item
        }

    }


}