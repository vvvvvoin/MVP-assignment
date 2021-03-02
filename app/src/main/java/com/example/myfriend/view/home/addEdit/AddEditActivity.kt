package com.example.myfriend.view.home.addEdit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.example.myfriend.R
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.databinding.ActivityAddEditBinding
import com.example.myfriend.view.home.detail.DetailActivity

class AddEditActivity : AppCompatActivity() {
    private val TAG = "AddEditActivity"
    private lateinit var binding: ActivityAddEditBinding

    companion object{
        const val EDIT_RESULT_OK = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit)
        setSupportActionBar(findViewById(R.id.add_edit_toolbar))
        val friendData = intent.getParcelableExtra<Friend>(DetailActivity.EXTRA_FRIEND_DATA)

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = AddEditFragment()
        val bundle = Bundle()
        if(friendData != null) bundle.putParcelable("FRIEND_DATA", friendData)
        fragment.arguments = bundle
        transaction.add(R.id.contentFrame, fragment)
        transaction.commit()

    }

}