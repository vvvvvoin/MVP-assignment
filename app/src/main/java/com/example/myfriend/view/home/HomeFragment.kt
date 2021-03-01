package com.example.myfriend.view.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentHomeBinding
import com.example.myfriend.view.nation.NationAdapter
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

        setHasOptionsMenu(true)
        return view
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_person_add ->{
                HomeFragmentDirections.actionHomeFragment2ToAddEditFragment(null).also {
                    findNavController().navigate(it) }
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

/*
    override fun openPhone(number: String) {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)))
    }

    override fun openEmail(toEmail: String) {
        val email = Intent(Intent.ACTION_SEND)
        email.type = "plain/text"
        val address = arrayOf(toEmail)
        email.putExtra(Intent.EXTRA_EMAIL, address)
        startActivity(email)
    }
*/

}