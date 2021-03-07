package com.example.myfriend.view.nation


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myfriend.databinding.ItemNationBinding
import com.example.myfriend.data.dataSource.remoteData.NationW
import java.util.*
import kotlin.collections.ArrayList


class NationAdapter : RecyclerView.Adapter<NationAdapter.ItemHolder>() {

    var nationList = ArrayList<NationW>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ItemHolder(ItemNationBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        nationItemListener?.let {
            holder.layout.setOnClickListener {
                nationItemListener?.onClickListener(it, nationList[position])
            }
        }
        holder.bind(nationList[position])
    }

    override fun getItemCount(): Int {
        return nationList.size
    }

    inner class ItemHolder(private val binding: ItemNationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val layout = binding.itemLayout
        val imageView: ImageView = binding.nationImage

        fun bind(item: NationW) {
            imageView.run {
                val uri = ("https://flagcdn.com/h80/" + item.alpha2Code.toLowerCase(Locale.ROOT) +".png").toUri()
                Glide.with(context).load(uri).override(Target.SIZE_ORIGINAL).into(this)
            }
            binding.item = item
        }
    }

    var nationItemListener : NationItemListener? = null
    interface NationItemListener{
        fun onClickListener(view : View, nationW : NationW)
    }

    fun onItemClick(listener: (view : View, nationW : NationW) -> Unit) {
        nationItemListener = object : NationItemListener{
            override fun onClickListener(view: View, nationW: NationW) {
                listener(view, nationW)
            }
        }
    }
}