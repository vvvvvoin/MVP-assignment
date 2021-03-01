package com.example.myfriend.view.nation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.model.vo.Nation
import com.example.myfriend.util.Event
import java.util.regex.Pattern

class NationPresenter(
    private val myRepository: MyRepository,
    private var isAddEdit: Boolean = false
) : NationContract.Presenter {
    private val TAG = "NationPresenter"

    private lateinit var view : NationContract.View
    private val resultNationList = myRepository.nationListResultObserve()
    private val resultNationFavorite = myRepository.favoriteNationResultObserve()

    private val _searchNation = MediatorLiveData<ArrayList<Nation>>()
    val searchNation: LiveData<ArrayList<Nation>>
        get() = _searchNation

    private val _nationFavorite = MediatorLiveData<com.example.myfriend.data.db.entity.Nation>()
    val nationFavorite: LiveData<com.example.myfriend.data.db.entity.Nation>
        get() = _nationFavorite

    var clickedNation : String = ""
    var clickedAlpha2Code : String = ""

    init {
        _searchNation.addSource(resultNationList) {
            if(isAddEdit){
                myRepository.initNationListResult()
                isAddEdit = false
            }
            _searchNation.value = it
        }
        _nationFavorite.addSource(resultNationFavorite){
            Log.d(TAG, it.toString())
            if(isAddEdit == false) {
                val nation =
                    com.example.myfriend.data.db.entity.Nation(clickedAlpha2Code, clickedNation)
                if (it.nation == "none") nation.nation = nation.nation + "*"
                _nationFavorite.value = nation
            }
        }
    }

    override fun setView(view: NationContract.View) {
        this.view = view
        this.view.setPresenter(this)
    }

    override fun openNationDetail(nation : Nation) {
        clickedNation = nation.name
        clickedAlpha2Code = nation.alpha2Code
        myRepository.getFavorite(nation.alpha2Code)
    }

    override fun searchNation(query: String) {
        myRepository.searchNation(query)
    }

}