package com.example.myfriend.view.home.detail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.myfriend.R
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.ActivityDetailBinding
import com.example.myfriend.view.home.addEdit.AddEditActivity
import com.example.myfriend.view.nation.detail.NationDetailActivity
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity(), DetailContract.View {
    private val TAG = "DetailActivity"
    private lateinit var mPresenter: DetailContract.Presenter
    companion object{
        const val EXTRA_FRIEND_DATA = "extraFriendData"
    }

    private val myRepository: MyRepository by inject()

    private lateinit var binding : ActivityDetailBinding
    private lateinit var friendData : Friend

    val requestActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        // action to do something
        if(activityResult.resultCode == AddEditActivity.EDIT_RESULT_OK){
            setResult(AddEditActivity.EDIT_RESULT_OK)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.detail_home_toolbar))

        friendData = intent.getParcelableExtra(EXTRA_FRIEND_DATA)!!
        binding.data = friendData

        mPresenter = DetailPresenter(myRepository)
        mPresenter.setView(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detail_edit_btn -> {
                val intent = Intent(this, AddEditActivity::class.java)
                intent.putExtra(EXTRA_FRIEND_DATA, friendData)
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

    override fun errorMessage(error: String) {

    }
}