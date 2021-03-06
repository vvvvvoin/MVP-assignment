package com.example.myfriend.view.tag.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.databinding.ItemFriendOnTagBinding
import kotlin.collections.ArrayList

class TagDetailAdapter(private val mPresenter: TagDetailPresenter) :
    RecyclerView.Adapter<TagDetailAdapter.ItemHolder>() {
    var friendList = ArrayList<Friend>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(ItemFriendOnTagBinding.inflate(LayoutInflater.from(parent.context), parent, false ).apply {
                presenter = mPresenter
            })

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(friendList[position])
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    inner class ItemHolder(private val binding: ItemFriendOnTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var layout = binding.layout

        @SuppressLint("SetTextI18n")
        fun bind(item: Friend) {
            binding.data = item
            if (item.number.equals("")) {
                binding.friendCallText.text = item.email
            } else if (item.email.equals("")) {
                binding.friendCallText.text = item.number
            } else {
                binding.friendCallText.text = item.number + ", " + item.email
            }
        }
    }

}