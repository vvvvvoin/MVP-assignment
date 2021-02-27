package com.example.myfriend.view.nation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.model.vo.Nation
import java.util.regex.Pattern

class NationPresenter(private val myRepository: MyRepository) : ViewModel(), NationContract.Presenter {
    private val TAG = "NationPresenter"

    private lateinit var view : NationContract.View
    private val resultNationList = myRepository.nationListResultObserve()

    private val _searchNation = MediatorLiveData<ArrayList<Nation>>()
    val searchNation: LiveData<ArrayList<Nation>>
        get() = _searchNation

    init {
        _searchNation.addSource(resultNationList){
            _searchNation.value = it
        }

    }

    override fun setView(view: NationContract.View) {
        this.view = view
        this.view.setPresenter(this)
    }

    override fun openNationDetail(query : String) {
        //view에서 openNationDetail파라미터에 해당 국가 정보를 담은 것을 받은 후에
        //repository에서 검색해서 나온 결과를
        // view.showNationDetail 파라미터에 담아서 실행

        view.showNationDetail("korea", true)
    }

    override fun searchNation(query: String) {
        myRepository.searchNation(query)
    }
}