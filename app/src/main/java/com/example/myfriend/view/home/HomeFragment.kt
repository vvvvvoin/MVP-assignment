package com.example.myfriend.view.home

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
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(), HomeContract.View {
    private val TAG = "HomeFragment"
    private lateinit var mPresenter: HomeContract.Presenter

    private lateinit var binding: FragmentHomeBinding
    private val myRepository: MyRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = HomePresenter(myRepository)
        mPresenter.setView(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val view = binding.root

        //데이터 바인딩으로 사사질 예정
        (mPresenter as HomePresenter).friendList.observe(viewLifecycleOwner, {
            Log.d(TAG, it.toString())
        })

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

}