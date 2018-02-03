package io.androidedu.datapersistance.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.ListPreference
import android.support.v7.preference.PreferenceFragmentCompat
import io.androidedu.datapersistance.R

// TODO (1) PreferenceFragmentCompat()'den extends et.
// TODO (2) onCreatePreferences() override et.
// TODO (3) res icine, xml directory'sini yarat.
// TODO (4) xml icine, pref_settings.xml'i yarat.
// TODO (5) onCreatePreferences()'de yaratilan xml'i @addPreferencesFromResource() yardimi ile inflate et.
// TODO (6) initEvent() icinde sharedPreference'dan ilgili key bilgileri ile degerleri oku ve ata.
// TODO (7) onDestroy() icinde sharedPreferences.unregisterOnSharedPreferenceChangeListener(this) ekle.

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val sharedPreferences by lazy { preferenceScreen.sharedPreferences }
    private val chkPrefIsBold by lazy { findPreference(resources.getString(R.string.key_isBold)) as CheckBoxPreference }
    private val chkPrefIsItalic by lazy { findPreference(resources.getString(R.string.key_isItalic)) as CheckBoxPreference }
    private val listPrefColorOption by lazy { findPreference(resources.getString(R.string.key_color_option)) as ListPreference }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        addPreferencesFromResource(R.xml.pref_settings)

        initEvent()
    }

    private fun initEvent() {

        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        getTypeFace()

        setColorOptionSummary()
    }

    private fun getTypeFace() {

        if (sharedPreferences.getBoolean(resources.getString(R.string.key_isBold), resources.getBoolean(R.bool.def_isBold))
                && sharedPreferences.getBoolean(resources.getString(R.string.key_isItalic), resources.getBoolean(R.bool.def_isItalic))) {

            (chkPrefIsBold).isChecked = true
            (chkPrefIsItalic).isChecked = true

        } else if (sharedPreferences.getBoolean(getString(R.string.key_isBold), resources.getBoolean(R.bool.def_isBold))) {

            (chkPrefIsBold).isChecked = true
            (chkPrefIsItalic).isChecked = false

        } else if (sharedPreferences.getBoolean(getString(R.string.key_isItalic), resources.getBoolean(R.bool.def_isItalic))) {

            (chkPrefIsBold).isChecked = false
            (chkPrefIsItalic).isChecked = true

        } else {

            (chkPrefIsBold).isChecked = false
            (chkPrefIsItalic).isChecked = false
        }
    }

    private fun setColorOptionSummary() {

        val colorOption = sharedPreferences.getString(resources.getString(R.string.key_color_option), resources.getString(R.string.red_value))

        listPrefColorOption.summary = getPrefColor(colorOption)
    }

    private fun getPrefColor(colorKey: String): String {

        return when (colorKey) {

            resources.getString(R.string.red_value) -> {

                resources.getString(R.string.red)
            }

            resources.getString(R.string.blue_value) -> {

                resources.getString(R.string.blue)
            }

            resources.getString(R.string.green_value) -> {

                resources.getString(R.string.green)
            }

            else -> {
                ""
            }
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {

        setColorOptionSummary()
    }

    override fun onDestroy() {
        super.onDestroy()

        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}