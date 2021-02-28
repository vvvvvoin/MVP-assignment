package com.example.myfriend.view.nation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.model.vo.Nation
import java.util.regex.Pattern

class NationPresenter(private val myRepository: MyRepository) : NationContract.Presenter {
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

    var isAddEdit : Boolean = false
    var clickedNation : String = ""

    init {
        _searchNation.addSource(resultNationList) {
            if(isAddEdit){
                myRepository.initNationListResult()
                isAddEdit = false
            }
            _searchNation.value = it
        }

        _nationFavorite.addSource(resultNationFavorite){
            Log.d(TAG, "이것이 반응해야한다")
            if(it.nation == "none"){
                view.showNationDetail(clickedNation, false)
            }else{
                view.showNationDetail(clickedNation, true)
            }
        }
    }

    override fun setView(view: NationContract.View) {
        this.view = view
        this.view.setPresenter(this)
    }

    override fun openNationDetail(nation : Nation) {
        //view에서 openNationDetail파라미터에 해당 국가 정보를 담은 것을 받은 후에
        //repository에서 검색해서 나온 결과를
        // view.showNationDetail 파라미터에 담아서 실행
        clickedNation = nation.alpha2Code
        myRepository.getFavorite(nation.name)

    }

    override fun searchNation(query: String) {
        myRepository.searchNation(query)
    }

}