package com.example.myfriend.view.home.addEdit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.databinding.ItemTagAddEditBinding
import com.example.myfriend.view.home.HomeAdapter

class AddEditTagAdapter(private val isAddEdit : Boolean) : RecyclerView.Adapter<AddEditTagAdapter.ItemHolder>() {

    var tagList = ArrayList<Tag>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            return ItemHolder(ItemTagAddEditBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AddEditTagAdapter.ItemHolder, position: Int) {
        if(isAddEdit){
            holder.tagImageView()
            holder.tagClear.setOnClickListener {
                tagList.removeAt(position)
                this.notifyDataSetChanged()
            }
        }else{
            holder.clearTagView()
            holder.itemLayout.setOnClickListener {
                tagItemClickListener?.onClickListener(it, tagList[position])
            }
        }
        holder.bind(tagList[position])
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    inner class ItemHolder(private val binding: ItemTagAddEditBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemLayout = binding.itemLayout
        var tagClear = binding.tagClear
        var tagImage = binding.tagImage

        fun bind(item: Tag) {
            binding.tag = item
        }

        fun clearTagView(){
            tagClear.visibility = View.GONE
        }

        fun tagImageView(){
            tagImage.visibility = View.GONE
        }
    }

    var tagItemClickListener: TagItemClickListener? = null

    interface TagItemClickListener {
        fun onClickListener(view: View, tag: Tag)
    }

    fun onTagItemClick(listener: (view: View, tag: Tag) -> Unit) {
        tagItemClickListener = object : TagItemClickListener {
            override fun onClickListener(view: View, tag: Tag) {
                listener(view, tag)
            }
        }
    }
}