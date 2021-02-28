package com.example.myfriend.view.nation

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentNationBinding
import com.example.myfriend.model.vo.Nation
import com.example.myfriend.view.home.HomeFragmentDirections
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit


class NationFragment : Fragment(), NationContract.View {
    private val TAG = "NationFragment"
    private lateinit var mPresenter: NationContract.Presenter

    private lateinit var binding: FragmentNationBinding
    private val myRepository: MyRepository by inject()
    //addEdit fragment 에서 넘어왔는지 boolean 으로 구분함
    private val args : NationFragmentArgs by navArgs()
    private val nationAdapter: NationAdapter by lazy {
        context?.let { NationAdapter(it) }!!
    }
    private var nationQuery = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "addEdit에서 넘어옴 ${args.isAddOrEdit}")
        mPresenter = NationPresenter(myRepository)
        mPresenter.setView(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nation, container, false)
        binding.apply {
            presenter = (mPresenter as NationPresenter)
            lifecycleOwner = this@NationFragment
        }

        (mPresenter as NationPresenter).nationFavorite.observe(viewLifecycleOwner, {
            Log.d("aeaweaweaweaw", it.toString())
        })
        val view = binding.root

        binding.nationRecycler.apply {
            adapter = nationAdapter
            setHasFixedSize(true)
        }

        nationAdapter.onItemClick { view, nation ->
            if(!args.isAddOrEdit) (mPresenter as NationPresenter).openNationDetail(nation)
        }

        view.findViewById<EditText>(R.id.search_edit_text).textChanges()
            .subscribeOn(Schedulers.io())
            .filter{ it.toString().length > 1 }
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(!args.isAddOrEdit) nationQuery = it.toString()
                mPresenter.searchNation(it.toString())
            }

        initView()

        return view
    }

    private fun initView(){
        if(args.isAddOrEdit){
            (mPresenter as NationPresenter).isAddEdit = true
            binding.searchEditText.text.clear()
        }else{
            binding.searchEditText.setText(nationQuery)
        }

    }
    override fun setPresenter(presenter: NationContract.Presenter) {
        mPresenter = presenter
    }

    override fun showNationDetail(
        data: String,
        check: Boolean
    ) {
        NationFragmentDirections.actionNationFragment2ToNationDetailFragment(data, check).also {
            findNavController().navigate(it) }
    }

    override fun errorMessage(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

}