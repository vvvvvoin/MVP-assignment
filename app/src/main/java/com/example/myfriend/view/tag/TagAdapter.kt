package com.example.myfriend.view.tag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.databinding.ItemTagTabBinding

class TagAdapter(private val context : Context) : RecyclerView.Adapter<TagAdapter.ItemHolder>() {

    var tagList = ArrayList<Tag>()
    private var checkMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(ItemTagTabBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        tagItemListener?.let {
            holder.itemLayout.setOnLongClickListener {
                tagList[position].isCheck = true
                setDeleteMode(true)
                notifyItemChanged(position)
                tagItemListener?.onLongClickListener(it, tagList[position])
                return@setOnLongClickListener true
            }
            holder.itemLayout.setOnClickListener {
                tagItemClickListener?.onClickListener(it, tagList[position])
            }
        }
        if (checkMode) {
            holder.checkBox.visibility = View.VISIBLE
        } else {
            holder.checkBox.visibility = View.GONE
        }
        holder.bind(tagList[position], position)

    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    fun setDeleteMode(checkMode: Boolean) {
        this.checkMode = checkMode
        notifyDataSetChanged()
    }

    fun clear() {
        checkMode = false
    }

    inner class ItemHolder(private val binding: ItemTagTabBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemLayout = binding.itemLayout
        var checkBox = binding.tagCheckBox


        fun bind(item: Tag, position: Int) {
            binding.tag = item
            checkBox.isChecked = tagList[position].isCheck

            checkBox.setOnClickListener {
                tagList[position].isCheck = checkBox.isChecked
            }
        }
    }

    var tagItemListener: TagItemListener? = null

    interface TagItemListener {
        fun onLongClickListener(view: View, tag: Tag)
    }

    fun onTagItemLongClick(listener: (view: View, tag: Tag) -> Unit) {
        tagItemListener = object : TagItemListener {
            override fun onLongClickListener(view: View, tag: Tag) {
                listener(view, tag)
            }
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