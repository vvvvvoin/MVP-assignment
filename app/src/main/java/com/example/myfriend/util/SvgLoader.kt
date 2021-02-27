package com.example.myfriend.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.PictureDrawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.pixplicity.sharp.Sharp
import okhttp3.*
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream


object SvgLoader {

    private val TAG = "SvgLoader"
    private var httpClient: OkHttpClient? = null

    fun fetchSvg(context: Context, url: String, imageView: ImageView) {
        if (httpClient == null) {
            httpClient = OkHttpClient.Builder()
                .cache(Cache(context.getCacheDir(), 5 * 1024 * 1014))
                .build()
        }

        // here we are making HTTP call to fetch data from URL.
        val request: Request = Request.Builder().url(url).build()

        httpClient!!.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val stream = response.body
                if (stream != null) {
                    Sharp.loadInputStream(stream.byteStream()).into(imageView)
                    Log.d(TAG, stream.byteStream().toString())
                }
            }
        })
    }

    //구글에서 제공하는 효율적으로 bitMap 로드 메서드
    private fun decodeSampledBitmapFromResource(
        res: InputStream,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        return BitmapFactory.Options().run { //this: BitmapFactory.Option
            inJustDecodeBounds = true

            //InputStream을 BufferedInputStream을 만들어 처리해준다
            val bufferedInputStream = BufferedInputStream(res)
            bufferedInputStream.mark(bufferedInputStream.available())

            BitmapFactory.decodeStream(bufferedInputStream, null, this)

            bufferedInputStream.reset()
            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false

            BitmapFactory.decodeStream(bufferedInputStream, null, this)
        }!!
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}