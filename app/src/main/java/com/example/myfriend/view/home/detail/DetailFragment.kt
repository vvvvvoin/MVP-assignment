package com.example.myfriend.view.home.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentAddEditBinding
import com.example.myfriend.databinding.FragmentDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import java.util.*


class DetailFragment : Fragment(), DetailContract.View {
    private val TAG = "DetailFragment"

    private lateinit var mPresenter: DetailContract.Presenter

    private lateinit var binding: FragmentDetailBinding
    private var bottomNavigationView : BottomNavigationView? = null

    private val myRepository: MyRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNavigationView= activity?.findViewById(R.id.bottom_nav)
        bottomNavigationView?.let {
            it.visibility = View.GONE
        }
        mPresenter = DetailPresenter(myRepository)
        mPresenter.setView(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        val view = binding.root

        binding.flagImageView.setOnClickListener {

        }

        setHasOptionsMenu(true)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detail_edit_btn -> {

            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun setPresenter(presenter: DetailContract.Presenter) {
        mPresenter = presenter
    }

    override fun errorMessage(error: String) {

    }
}