package com.example.myfriend.view.setting

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myfriend.R
import com.example.myfriend.databinding.FragmentSettingBinding
import com.example.myfriend.view.MainActivity
import java.util.*


class SettingFragment : Fragment() {
    private val TAG = "SettingFragment"
    private lateinit var binding : FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        val view = binding.root
        val locale = getSystemLocale(requireContext().resources.configuration)
        var appLang = ""
        Log.d(TAG, locale?.displayLanguage.toString())
        if (locale?.displayLanguage == "영어" || locale?.displayLanguage == "English") {
            appLang = getString(R.string.english)
            binding.koreanRadio.isChecked = false
            binding.englishRadio.isChecked = true
        } else {
            appLang = getString(R.string.korean)
            binding.koreanRadio.isChecked = true
            binding.englishRadio.isChecked = false
        }

        binding.koreanRadio.setOnClickListener {
            binding.englishRadio.isChecked = false
            if(appLang != getString(R.string.korean))
                changeLanguage(it)
        }

        binding.englishRadio.setOnClickListener {
            binding.koreanRadio.isChecked = false
            if(appLang != getString(R.string.english))
                changeLanguage(it)
        }

        return view
    }

    //낮은 버전의 sdk 전용
    fun getSystemLocaleLegacy(config: Configuration): Locale? {
        return config.locale
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun getSystemLocale(config: Configuration): Locale? {
        return config.getLocales().get(0)
    }

    //낮은 버전의 sdk 전용
    fun setSystemLocaleLegacy(config: Configuration, locale: Locale) {
        config.locale = locale
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun setSystemLocale(config: Configuration, locale: Locale?) {
        config.setLocale(locale)
    }

    private fun changeLanguage(view: View) {
        val lang = view.tag as String
        LocaleWrapper.setLocale(lang)
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }

}

object LocaleWrapper {
    private var sLocale: Locale? = null

    fun wrap(base: Context): Context {
        if (sLocale == null) {
            return base
        }
        val res: Resources = base.resources
        val config = res.configuration
        config.setLocale(sLocale)
        return base.createConfigurationContext(config)
    }

    fun setLocale(lang: String?) {
        sLocale = Locale(lang)
    }
}
