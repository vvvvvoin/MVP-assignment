package com.example.myfriend.view.home.detail


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject


class DetailFragment : Fragment(), DetailContract.View {
    private val TAG = "DetailFragment"

    private lateinit var mPresenter: DetailContract.Presenter

    private lateinit var binding: FragmentDetailBinding
    private var bottomNavigationView : BottomNavigationView? = null

    private val myRepository: MyRepository by inject()
    //private val args : DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNavigationView= activity?.findViewById(R.id.bottom_nav)
        bottomNavigationView?.let {
            it.visibility = View.GONE
        }
        //mPresenter = DetailPresenter(myRepository)
        mPresenter.setView(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
       // binding.data = args.friend
        val view = binding.root

        setHasOptionsMenu(true)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detail_edit_btn -> {
                //addEdit으로 넘기는 args로서
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



    override fun openNumberApp(number: String) {

    }

    override fun openEmailApp(toEmail: String) {

    }
}