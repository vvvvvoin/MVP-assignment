package com.example.myfriend.view.tag.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.ActivityTagDetailBinding
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

    private lateinit var binding : ActivityTagDetailBinding
    private val myRepository: MyRepository by inject()

    private val tagDetailAdapter : TagDetailAdapter by lazy {
        TagDetailAdapter(mPresenter as TagDetailPresenter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tagName = intent.getStringExtra(EXTRA_TAG_NAME)!!
        mPresenter = TagDetailPresenter(myRepository, tagName)
        mPresenter.setView(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tag_detail)
        binding.apply {
            lifecycleOwner = this@TagDetailActivity
            presenter = (mPresenter as TagDetailPresenter)
        }
        setSupportActionBar(binding.tagDetailToolbar)
        supportActionBar?.title = tagName

        binding.friendRecycler.apply {
            adapter = tagDetailAdapter
            setHasFixedSize(true)
        }

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