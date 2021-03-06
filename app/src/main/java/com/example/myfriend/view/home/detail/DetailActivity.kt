package com.example.myfriend.view.home.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.myfriend.R
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.ActivityDetailBinding
import com.example.myfriend.util.EventObserver
import com.example.myfriend.view.home.addEdit.AddEditActivity
import com.example.myfriend.view.home.addEdit.AddEditTagAdapter
import com.example.myfriend.view.tag.detail.TagDetailActivity
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity(), DetailContract.View {
    private val TAG = "DetailActivity"
    private lateinit var mPresenter: DetailContract.Presenter
    companion object{
        const val EXTRA_FRIEND_DATA = "extraFriendData"
        const val EXTRA_TAG_DATA = "extraTagData"
    }

    private val myRepository: MyRepository by inject()
    private val tagAdapter: AddEditTagAdapter by lazy {
        AddEditTagAdapter(false)
    }

    private lateinit var binding : ActivityDetailBinding
    private lateinit var friendData : Friend
    private lateinit var tagList : ArrayList<Tag>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        friendData = intent.getParcelableExtra(EXTRA_FRIEND_DATA)!!

        mPresenter = DetailPresenter(myRepository, friendData.id)
        mPresenter.setView(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.apply {
            presenter = mPresenter as DetailPresenter
            data = friendData
        }
        setSupportActionBar(binding.detailHomeToolbar)
        supportActionBar?.title = friendData.name

        binding.tagItemRecycler.apply {
            adapter = tagAdapter
            setHasFixedSize(true)
        }

        (mPresenter as DetailPresenter).tagList.observe(this, {
            Log.d(TAG, it.toString())
            if(it.isEmpty()){
                tagList = ArrayList()
                tagAdapter.tagList = ArrayList()
                tagAdapter.notifyDataSetChanged()
            }else{
                tagList = ArrayList(it)
                tagAdapter.tagList = ArrayList(it)
                tagAdapter.notifyDataSetChanged()
            }
        })

        tagAdapter.onTagItemClick { view, tag ->
            val intent = Intent(this, TagDetailActivity::class.java)
            intent.putExtra(TagDetailActivity.EXTRA_TAG_NAME, tag.tagName)
            startActivity(intent)
        }

        setSupportActionBar(findViewById(R.id.detail_home_toolbar))
        initObserver()
    }

    private fun initObserver() {
        myRepository.error.observe(this, EventObserver{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

    private val requestActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        // action to do something
        if(activityResult.resultCode == AddEditActivity.EDIT_RESULT_OK){
            setResult(AddEditActivity.EDIT_RESULT_OK)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detail_edit_btn -> {
                val intent = Intent(this, AddEditActivity::class.java)
                intent.putExtra(EXTRA_FRIEND_DATA, friendData)
                intent.putParcelableArrayListExtra(EXTRA_TAG_DATA, tagList)
                requestActivity.launch(intent)
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun setPresenter(presenter: DetailContract.Presenter) {
        mPresenter = presenter
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