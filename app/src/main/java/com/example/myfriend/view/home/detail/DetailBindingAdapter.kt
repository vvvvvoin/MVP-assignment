package com.example.myfriend.view.home.detail

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import java.util.*


@BindingAdapter("setProfile")
fun setProfileImage(view: ImageView, uri: String?) {
    uri?.let {
        Glide.with(view.context)
            .load(it.toUri())
            .into(view)
    }
}

@BindingAdapter("setFlag")
fun setFlagImage(view: ImageView, str: String?) {
    str?.let {
        val uri = ("https://flagcdn.com/h80/" + it.toLowerCase(Locale.ROOT) +".png").toUri()
        Glide.with(view.context).load(uri).override(Target.SIZE_ORIGINAL).into(view)
    }
}