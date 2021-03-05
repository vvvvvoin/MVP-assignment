package com.example.myfriend.view.home.detail

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myfriend.R
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.view.home.HomeAdapter
import com.example.myfriend.view.home.addEdit.AddEditTagAdapter
import com.example.myfriend.view.tag.TagAdapter
import java.util.*


@BindingAdapter("setProfile")
fun setProfileImage(view: ImageView, uri: String?) {
    if(uri != "null" && uri != null ){
        Glide.with(view.context)
            .load(uri.toUri())
            .into(view)
    }else{
        Glide.with(view.context).load(R.drawable.ic_default_prefile).into(view)
    }
}

@BindingAdapter("setFlag")
fun setFlagImage(view: ImageView, str: String?) {
    str?.let {
        val uri = ("https://flagcdn.com/h80/" + it.toLowerCase(Locale.ROOT) +".png").toUri()
        Glide.with(view.context).load(uri).override(Target.SIZE_ORIGINAL).into(view)
    }
}

