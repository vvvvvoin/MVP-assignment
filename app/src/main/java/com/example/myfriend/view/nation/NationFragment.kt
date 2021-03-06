package com.example.myfriend.view.nation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.example.myfriend.R
import com.example.myfriend.data.db.entity.Nation
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentNationBinding
import com.example.myfriend.view.home.addEdit.AddEditFragment
import com.example.myfriend.view.nation.detail.NationDetailActivity
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit


class NationFragment : Fragment(), NationContract.View {
    private val TAG = "NationFragment"
    private lateinit var mPresenter: NationContract.Presenter
    companion object{
        const val IS_ADD_OR_EDIT = "IS_ADD_OR_EDIT"
    }
    private lateinit var binding: FragmentNationBinding
    private val myRepository: MyRepository by inject()

    //addEdit fragment 에서 넘어왔는지 boolean 으로 구분함
    private val nationAdapter: NationAdapter by lazy {
        NationAdapter()
    }
    private var nationQuery = ""
    private var isAddOrEdit : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            isAddOrEdit = it.getBoolean(IS_ADD_OR_EDIT)
        }

        mPresenter = NationPresenter(myRepository, isAddOrEdit)
        mPresenter.setView(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nation, container, false)
        binding.apply {
            presenter = (mPresenter as NationPresenter)
            lifecycleOwner = this@NationFragment
        }

        val view = binding.root
        binding.nationRecycler.apply {
            adapter = nationAdapter
            setHasFixedSize(true)
        }

        nationAdapter.onItemClick { view, nation ->
            if (isAddOrEdit == false) {
                (mPresenter as NationPresenter).openNationDetail(nation)
            } else {
                //클릭된 nation정보를 넘김
                val bundle = Bundle()
                bundle.putParcelable(AddEditFragment.ADD_OR_EDIT_BUNDLE_KEY, nation)
                setFragmentResult(AddEditFragment.ADD_OR_EDIT_REQUEST_KEY, bundle)
                activity?.onBackPressed()
            }
        }

        binding.searchEditText.textChanges()
            .subscribeOn(Schedulers.io())
            .filter{ it.toString().length > 1 }
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(!isAddOrEdit) nationQuery = it.toString()
                mPresenter.searchNation(it.toString())
            }

        initView()
        initObserver()

        return view
    }

    private fun initView(){
        if(isAddOrEdit){
            binding.searchEditText.text.clear()
        }else{
            binding.searchEditText.setText(nationQuery)
        }
    }

    private fun initObserver() {
        (mPresenter as NationPresenter).nationFavorite.observe(viewLifecycleOwner, {
            if (it.nation == "*" || it.alpha2Code.isEmpty()) {
                return@observe
            }else{
                var check = true
                val intent = Intent(context, NationDetailActivity::class.java)
                intent.putExtra(NationDetailActivity.EXTRA_NATION_DATA, it)
                if (it.nation.contains("*")) check = false
                intent.putExtra(NationDetailActivity.EXTRA_IS_CHECKED, check)
                startActivity(intent)
            }
        })
    }

    override fun setPresenter(presenter: NationContract.Presenter) {
        mPresenter = presenter
    }

    override fun onDestroyView() {
        mPresenter.detachView()
        super.onDestroyView()
    }

    override fun showNationDetail(
        data: Nation,
        check: Boolean
    ) {

    }

    override fun errorMessage(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

}