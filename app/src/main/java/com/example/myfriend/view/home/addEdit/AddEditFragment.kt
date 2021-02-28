package com.example.myfriend.view.home.addEdit

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentAddEditBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject


class AddEditFragment : Fragment(), AddEditContract.View {
    private val TAG = "AddEditFragment"

    private lateinit var mPresenter: AddEditContract.Presenter

    private lateinit var binding: FragmentAddEditBinding
    private var bottomNavigationView : BottomNavigationView? = null

    //이 view 를 불러오는 view 로부터 id 값이 존재하면 기존에 있는 것을 수정하는 것이고
    //id 값이 없을 경우 새롭게 추가하는 항목이다
    private val args: AddEditFragmentArgs by navArgs()
    private val myRepository: MyRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNavigationView= activity?.findViewById(R.id.bottom_nav)
        bottomNavigationView?.let {
            it.visibility = View.GONE
        }

        mPresenter = AddEditPresenter(myRepository, args.friendId)
        mPresenter.setView(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit, container, false)
        val view = binding.root

        binding.flagImageView.setOnClickListener {
            AddEditFragmentDirections.actionAddEditFragmentToNationFragment3(true).also {
                findNavController().navigate(it)
            }
        }


        setHasOptionsMenu(true)

        return view
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_edit_btn -> {
                mPresenter.addEdit(binding.numberEdit.text.toString(), binding.numberEdit.text.toString(), binding.emailEdit.text.toString(), "flag", "")
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.addedit_menu, menu)
    }

    override fun setPresenter(presenter: AddEditContract.Presenter) {
        mPresenter = presenter
    }

    override fun errorMessage(error: String) {

    }

    override fun completeAddEdit() {
        bottomNavigationView?.let {
            it.visibility = View.VISIBLE
        }
        activity?.onBackPressed()
    }

}