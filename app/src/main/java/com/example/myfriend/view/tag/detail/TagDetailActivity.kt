package com.example.myfriend.view.tag.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentHomeBinding
import com.example.myfriend.view.home.HomeAdapter
import com.example.myfriend.view.home.HomeContract
import com.example.myfriend.view.home.detail.DetailActivity
import org.koin.android.ext.android.inject

class TagDetailActivity : AppCompatActivity(), TagDetailContract.View {
    private val TAG = "TagDetailActivity"

    private lateinit var mPresenter: TagDetailContract.Presenter
    companion object{
        const val EXTRA_TAG_NAME = "extraTagName"
    }

    private val myRepository: MyRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_detail)

        val tagName = intent.getStringExtra(EXTRA_TAG_NAME)!!
        Log.d(TAG, "받아온 태그는 $tagName")
        mPresenter = TagDetailPresenter(myRepository, tagName)
    }

    override fun setPresenter(presenter: TagDetailContract.Presenter) {
        mPresenter = presenter
    }

    override fun errorMessage(error: String) {

    }

    override fun openNumberApp(number: String) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number)))
    }

    override fun openEmailApp(toEmail: String) {
        val email = Intent(Intent.ACTION_SEND)
        email.type = "plain/text"
        val address = arrayOf(toEmail)
        email.putExtra(Intent.EXTRA_EMAIL, address)
        startActivity(email)
    }
}