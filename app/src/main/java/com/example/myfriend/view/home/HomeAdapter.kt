package com.example.myfriend.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.databinding.ItemFirendBinding
import com.example.myfriend.databinding.ItemNationBinding
import com.example.myfriend.model.vo.Nation
import java.util.*
import kotlin.collections.ArrayList

class HomeAdapter(private val mPresenter : HomePresenter) : RecyclerView.Adapter<HomeAdapter.ItemHolder>() {
    var friendList = ArrayList<Friend>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(ItemFirendBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            mPresenter
        }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(friendList[position])
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    inner class ItemHolder(private val binding: ItemFirendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Friend) {
            binding.data = item
        }
    }

}