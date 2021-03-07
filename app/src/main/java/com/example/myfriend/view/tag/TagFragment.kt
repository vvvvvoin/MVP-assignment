package com.example.myfriend.view.tag

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentTagBinding
import com.example.myfriend.view.home.ListOrderType
import com.example.myfriend.view.tag.detail.TagDetailActivity
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
    private var deleteMode = false

    private val tagAdapter : TagAdapter by lazy {
        TagAdapter(requireContext())
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
                if(it.isNullOrEmpty()){
                    //공백으로 만들기
                    mPresenter.searchTag("")
                    binding.plzSearchTextView.visibility = View.VISIBLE
                    binding.tagRecycler.visibility = View.INVISIBLE
                }else{
                    mPresenter.searchTag(it.toString())
                    binding.plzSearchTextView.visibility = View.GONE
                    binding.tagRecycler.visibility = View.VISIBLE
                }
            }

        tagAdapter.onTagItemLongClick { view, tag ->
            deleteMode = true
            activity?.invalidateOptionsMenu()
        }

        tagAdapter.onTagItemClick { view, tag ->
            if(!deleteMode){
                val intent = Intent(context, TagDetailActivity::class.java)
                intent.putExtra(TagDetailActivity.EXTRA_TAG_NAME, tag.tagName)
                startActivity(intent)
            }
        }


        setDeleteMode(deleteMode)
        setHasOptionsMenu(true)
        activity?.invalidateOptionsMenu()
        return view
    }

    private fun setDeleteMode(boolean: Boolean){
        if(!boolean) binding.searchEditText.text.clear()
        deleteMode = boolean
        tagAdapter.setDeleteMode(boolean)
    }

    override fun setPresenter(presenter: TagContract.Presenter) {
        mPresenter = presenter
    }

    override fun onDestroyView() {
        tagAdapter.clear()
        mPresenter.detachView()
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tag_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tag_check -> {
                if(binding.searchEditText.text.toString().isEmpty()) return true
                setDeleteMode(true)
                activity?.invalidateOptionsMenu()
            }
            R.id.delete_btn -> {
                mPresenter.deleteTag(tagAdapter.tagList)
                tagAdapter.tagList.clear()
                tagAdapter.notifyDataSetChanged()
                setDeleteMode(false)
                activity?.invalidateOptionsMenu()
            }
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

    override fun onPrepareOptionsMenu(menu: Menu) {
        val checkBtn = menu.findItem(R.id.tag_check)
        val deleteBtn = menu.findItem(R.id.delete_btn)
        val nameOrderBtn= menu.findItem(R.id.order_name_tag)
        val seqOrderBtn= menu.findItem(R.id.order_seq_tag)

        if(deleteMode == false){
            checkBtn.isVisible = true
            nameOrderBtn.isVisible = true
            seqOrderBtn.isVisible = true
            deleteBtn.isVisible = false
        }else{
            checkBtn.isVisible = false
            nameOrderBtn.isVisible = false
            seqOrderBtn.isVisible = false
            deleteBtn.isVisible = true
        }
    }


}