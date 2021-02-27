package com.example.myfriend.view.tag

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.myfriend.R

class TagFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tag, container, false)
        setHasOptionsMenu(true)
        return view
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tag_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return true
    }
}