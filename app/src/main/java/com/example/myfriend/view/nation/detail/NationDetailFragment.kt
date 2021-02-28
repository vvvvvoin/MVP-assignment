package com.example.myfriend.view.nation.detail

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.view.nation.NationContract
import com.example.myfriend.view.nation.NationPresenter
import org.koin.android.ext.android.inject
import java.util.*

class NationDetailFragment : Fragment(), NationDetailContract.View{
    private val TAG = "NationDetailFragment"
    private lateinit var mPresenter: NationDetailContract.Presenter

    private val myRepository: MyRepository by inject()
    private val nationDetailFragmentArgs :NationDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = NationDetailPresenter(myRepository)
        mPresenter.setView(this)
        val view = inflater.inflate(R.layout.fragment_nation_detail, container, false)
        val nationName =  nationDetailFragmentArgs.nation
        val isCheck =  nationDetailFragmentArgs.isChecked
        val uri = ("https://flagcdn.com/h240/" + nationName.toLowerCase(Locale.ROOT) +".png").toUri()
        val imageView = view.findViewById<ImageView>(R.id.nation_image)

        if(isCheck){
            Log.d(TAG, "별표")
        }else{
            Log.d(TAG, "별표x")
        }

        view.findViewById<TextView>(R.id.nation_name_text).text =nationName

        Glide.with(this).load(uri).override(Target.SIZE_ORIGINAL).into(imageView)

        setHasOptionsMenu(true)
        return view
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nation_detail_menu, menu)
    }

    override fun setPresenter(presenter: NationDetailContract.Presenter) {
        mPresenter = presenter
    }

    override fun errorMessage(error: String) {

    }
}