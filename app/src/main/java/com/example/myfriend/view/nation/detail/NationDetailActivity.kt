package com.example.myfriend.view.nation.detail

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myfriend.R
import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.ActivityNationDetailBinding
import com.example.myfriend.util.EventObserver
import org.koin.android.ext.android.inject
import java.util.*


class NationDetailActivity : AppCompatActivity(), NationDetailContract.View {
    private val TAG = "NationDetailActivity"
    companion object{
        const val EXTRA_NATION_DATA = "NATION_DATA"
        const val EXTRA_IS_CHECKED = "IS_CHECKED"
    }

    private lateinit var mPresenter: NationDetailContract.Presenter

    private val myRepository: MyRepository by inject()

    private lateinit var binding : ActivityNationDetailBinding
    private lateinit var nationData : Nation
    private lateinit var uri : Uri
    private var isCheck : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nation_detail)
        mPresenter = NationDetailPresenter(myRepository)
        mPresenter.setView(this)

        binding  = DataBindingUtil.setContentView(this, R.layout.activity_nation_detail)
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.title = ""

        nationData = intent.getParcelableExtra(EXTRA_NATION_DATA)!!
        isCheck = intent.getBooleanExtra(EXTRA_IS_CHECKED, false)

        nationData.let {
            binding.item = nationData
            uri = ("https://flagcdn.com/h240/" + it.alpha2Code.toLowerCase(Locale.ROOT) +".png").toUri()
        }
        Glide.with(this).load(uri).override(Target.SIZE_ORIGINAL).into(binding.nationImage)

        invalidateOptionsMenu()
        initErrorObserver()
    }

    private fun initErrorObserver() {
        myRepository.error.observe(this, EventObserver{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nation_detail_star_uncheck -> {
                isCheck = true
                mPresenter.setFavorite(nationData, true)
                invalidateOptionsMenu()
                return true
            }
            R.id.nation_detail_star_checked -> {
                isCheck = false
                mPresenter.setFavorite(nationData, false)
                invalidateOptionsMenu()
                return true
            }
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val uncheck = menu?.findItem(R.id.nation_detail_star_uncheck)
        val check = menu?.findItem(R.id.nation_detail_star_checked)

        if(isCheck == true) {
            uncheck?.isVisible = false
            check?.isVisible = true
        }else{
            uncheck?.isVisible = true
            check?.isVisible = false
        }
        return true
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nation_detail_menu, menu)
        return true
    }

    override fun setPresenter(presenter: NationDetailContract.Presenter) {
        mPresenter = presenter
    }

}