package com.example.myfriend.view.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentHomeBinding
import com.example.myfriend.view.home.addEdit.AddEditActivity
import com.example.myfriend.view.home.addEdit.AddEditFragment
import com.example.myfriend.view.home.detail.DetailActivity
import com.example.myfriend.view.nation.NationAdapter
import com.example.myfriend.view.nation.detail.NationDetailActivity
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(), HomeContract.View {
    private val TAG = "HomeFragment"
    private lateinit var mPresenter: HomeContract.Presenter

    private lateinit var binding: FragmentHomeBinding
    private val myRepository: MyRepository by inject()

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(mPresenter as HomePresenter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = HomePresenter(myRepository)
        mPresenter.setView(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.apply {
            presenter = (mPresenter as HomePresenter)
            lifecycleOwner = this@HomeFragment
        }

        val view = binding.root
        binding.friendRecycler.apply {
            adapter = homeAdapter
            setPresenter(mPresenter)
            setHasFixedSize(true)
        }
        homeAdapter.onItemClick { view, friend ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_FRIEND_DATA, friend)
            requestActivity.launch(intent)
        }

        setHasOptionsMenu(true)
        return view
    }

    val requestActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if(activityResult.resultCode == AddEditActivity.EDIT_RESULT_OK){
            myRepository.getFriendList(ListOrderType.NAME)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_person_add ->{
                val intent = Intent(context, AddEditActivity::class.java)
                requestActivity.launch(intent)
            }
            R.id.order_name ->{
                mPresenter.setOrder(ListOrderType.NAME)
            }
            R.id.order_seq -> {
                mPresenter.setOrder(ListOrderType.SEQ)
            }
        }
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
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