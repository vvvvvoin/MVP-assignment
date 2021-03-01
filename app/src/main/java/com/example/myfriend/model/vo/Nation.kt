package com.example.myfriend.model.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nation(
    var name: String,
    var topLevelDomain: ArrayList<String>,
    var alpha2Code: String,
    var alpha3Code: String,
    var callingCodes: ArrayList<String>,
    var capital: String,
    var altSpellings: ArrayList<String>,
    var region: String,
    var subregion: String,
    var population: Int,
    var latlng: ArrayList<Double>,
    var demonym: String,
    var area: Double,
    var gini: Double,
    var timezones: ArrayList<String>,
    var borders: ArrayList<String>,
    var nativeName: String,
    var numericCode: Int,
    var currencies: ArrayList<Currencies>,
    var languages: ArrayList<Languages>,
    var translations: HashMap<String, String>,
    var flag: String,
    var regionalBlocs: ArrayList<RegionalBlocs>,
    var cioc: String
) : Parcelable {

    @Parcelize
    data class Currencies(
        var code: String,
        var name: String,
        var symbol: String
    ) : Parcelable

    @Parcelize
    data class Languages(
        var iso639_1: String,
        var iso639_2: String,
        var name: String,
        var nativeName: String
    ) : Parcelable

    @Parcelize
    data class RegionalBlocs(
        var acronym : String,
        var name : String,
        var otherAcronyms : ArrayList<String>,
        var otherNames: ArrayList<String>
    ) : Parcelable
}