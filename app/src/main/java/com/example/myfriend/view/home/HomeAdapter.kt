package com.example.myfriend.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.databinding.ItemFriendBinding
import kotlin.collections.ArrayList

class HomeAdapter(private val mPresenter : HomePresenter) : RecyclerView.Adapter<HomeAdapter.ItemHolder>() {
    var friendList = ArrayList<Friend>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            presenter = mPresenter
        })


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        homeItemListener?.let {
            holder.layout.setOnClickListener {
                homeItemListener?.onClickListener(it, friendList[position])
            }
        }
        holder.bind(friendList[position])
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    inner class ItemHolder(private val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var layout = binding.layout

        @SuppressLint("SetTextI18n")
        fun bind(item: Friend) {
            binding.data = item
            if (item.number.equals("")) {
                binding.friendCallText.text = item.email
            } else if (item.email.equals("")) {
                binding.friendCallText.text = item.number
            }else{
                binding.friendCallText.text = item.number + ", " +  item.email
            }
        }
    }

    var homeItemListener: HomeItemListener? = null

    interface HomeItemListener {
        fun onClickListener(view: View, friend: Friend)
    }

    fun onItemClick(listener: (view: View, friend: Friend) -> Unit) {
        homeItemListener = object : HomeItemListener {
            override fun onClickListener(view: View, friend: Friend) {
                listener(view, friend)
            }
        }
    }
}