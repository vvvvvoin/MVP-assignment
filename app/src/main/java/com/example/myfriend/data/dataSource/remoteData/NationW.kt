package com.example.myfriend.data.dataSource.remoteData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class NationW(
    var name: String,
    var topLevelDomain: ArrayList<String>? = null,
    var alpha2Code: String,
    var alpha3Code: String?= null,
    var callingCodes: ArrayList<String>?= null,
    var capital: String?= null,
    var altSpellings: ArrayList<String>?= null,
    var region: String?= null,
    var subregion: String?= null,
    var population: Int?= null,
    var latlng: ArrayList<Double>?= null,
    var demonym: String?= null,
    var area: Double?= null,
    var gini: Double?= null,
    var timezones: ArrayList<String>?= null,
    var borders: ArrayList<String>?= null,
    var nativeName: String?= null,
    var numericCode: Int?= null,
    var currencies: ArrayList<Currencies>?= null,
    var languages: ArrayList<Languages>?= null,
    var translations: HashMap<String, String>?= null,
    var flag: String?= null,
    var regionalBlocs: ArrayList<RegionalBlocs>?= null,
    var cioc: String?= null
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