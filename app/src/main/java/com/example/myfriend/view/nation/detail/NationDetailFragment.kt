package com.example.myfriend.view.nation.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.myfriend.R

class NationDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nation_detail, container, false)
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
}