package com.example.myfriend.view.nation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.data.dataSource.remoteData.NationW
import com.example.myfriend.data.db.entity.Nation

class NationPresenter(
    private val myRepository: MyRepository,
    private var isAddEdit: Boolean = false
) : NationContract.Presenter {
    private val TAG = "NationPresenter"

    private lateinit var view : NationContract.View
    private val resultNationList = myRepository.nationListResultObserve()
    private val resultNationFavorite = myRepository.favoriteNationResultObserve()

    private val _searchNation = MediatorLiveData<ArrayList<NationW>>()
    val searchNationW: LiveData<ArrayList<NationW>>
        get() = _searchNation

    private val _nationFavorite = MediatorLiveData<Nation>()
    val nationFavorite: LiveData<Nation>
        get() = _nationFavorite

    private var clickedNation : String = ""
    private var clickedAlpha2Code : String = ""

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
                val nation = Nation(clickedAlpha2Code, clickedNation)
                if (it.nation == "none") nation.nation = nation.nation + "*"
                _nationFavorite.value = nation
            }
        }
    }

    override fun setView(view: NationContract.View) {
        this.view = view
        this.view.setPresenter(this)
    }

    override fun openNationDetail(nationW : NationW) {
        clickedNation = nationW.name
        clickedAlpha2Code = nationW.alpha2Code
        myRepository.getFavorite(nationW.alpha2Code)
    }

    override fun searchNation(query: String) {
        myRepository.searchNation(query)
    }

}