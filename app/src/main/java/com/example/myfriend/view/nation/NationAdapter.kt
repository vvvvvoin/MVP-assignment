package com.example.myfriend.view.nation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myfriend.databinding.ItemNationBinding
import com.example.myfriend.model.vo.Nation
import com.example.myfriend.util.SvgLoader


class NationAdapter(private val context : Context) : RecyclerView.Adapter<NationAdapter.ItemHolder>() {

    var nationList = ArrayList<Nation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ItemHolder(ItemNationBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(nationList[position])
    }

    override fun getItemCount(): Int {
        return nationList.size
    }

    inner class ItemHolder(private val binding: ItemNationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.nationImage
        fun bind(item: Nation) {
            imageView.run {
                SvgLoader.fetchSvg(context, item.flag, imageView)
                //더 좋은 방법이 필요하다
                //Glide.with(context).load(item.flag).override(Target.SIZE_ORIGINAL).into(this)
            }
            binding.item = item
        }
    }
}