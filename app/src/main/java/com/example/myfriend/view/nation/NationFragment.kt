package com.example.myfriend.view.nation

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentNationBinding
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
    private val nationAdapter: NationAdapter by lazy {
        context?.let { NationAdapter(it) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = NationPresenter(myRepository)
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

        view.findViewById<EditText>(R.id.search_edit_text).textChanges()
            .subscribeOn(Schedulers.io())
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mPresenter.searchNation(it.toString())
                Log.d("test", it.toString())
            }


        val imageView = view.findViewById<ImageView>(R.id.search_ic)

        setHasOptionsMenu(true)
        return view
    }


    override fun setPresenter(presenter: NationContract.Presenter) {
        mPresenter = presenter
    }

    override fun showNationDetail(data: String, check: Boolean) {
        // bundle이든 뭐시기든 값만 넘기면됨
        //Navigation.findNavController(requireView()).navigate(R.id.action_nationFragment2_to_nationDetailFragment)
    }

    override fun errorMessage(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

}