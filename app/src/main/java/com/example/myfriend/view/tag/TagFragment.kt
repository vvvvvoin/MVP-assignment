package com.example.myfriend.view.tag

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myfriend.R
import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentNationBinding
import com.example.myfriend.databinding.FragmentTagBinding
import com.example.myfriend.view.home.ListOrderType
import com.example.myfriend.view.nation.NationContract
import com.example.myfriend.view.nation.NationFragment
import com.example.myfriend.view.nation.NationPresenter
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject

class TagFragment : Fragment(), TagContract.View{
    private val TAG = "TagFragment"
    private lateinit var mPresenter: TagContract.Presenter

    private lateinit var binding: FragmentTagBinding
    private val myRepository: MyRepository by inject()

    private var defaultListOrderType = ListOrderType.NAME

    private val tagAdapter : TagAdapter by lazy {
        TagAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = TagPresenter(myRepository)
        mPresenter.setView(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tag, container, false)
        binding.apply {
            lifecycleOwner = this@TagFragment
            presenter = (mPresenter as TagPresenter)
        }
        val view = binding.root

        binding.tagRecycler.apply {
            adapter = tagAdapter
            setHasFixedSize(true)
        }

        binding.searchEditText.textChanges()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                mPresenter.searchTag(it.toString())
            }

        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tag_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tag_check -> {}
            R.id.order_name_tag -> {
                mPresenter.setOrder(ListOrderType.NAME)
                defaultListOrderType = ListOrderType.NAME
            }
            R.id.order_seq_tag -> {
                mPresenter.setOrder(ListOrderType.SEQ)
                defaultListOrderType = ListOrderType.SEQ
            }
        }
        return true
    }

    override fun setPresenter(presenter: TagContract.Presenter) {

    }

    override fun showTagDetail() {

    }

    override fun errorMessage(error: String) {

    }
}