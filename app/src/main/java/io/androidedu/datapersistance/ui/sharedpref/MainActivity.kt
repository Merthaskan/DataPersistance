package io.androidedu.datapersistance.ui.sharedpref

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import io.androidedu.datapersistance.R

// TODO (1) res icine, menu directory'sini yarat.
// TODO (2) menu icine, menu.xml'i yarat.
// TODO (3) oncreateOptionsMenu()'de yaratilan xml'i @menuInflater yardimi ile inflate et.
// TODO (4) onOptionsItemSelected()'i override et ve R.id.menu_activity_main_settings case'i icin startActivity ile SettingsActivity'e yonlen.
// TODO (5) sharedPreferences.registerOnSharedPreferenceChangeListener(this) ekle.
// TODO (6) onSharedPreferenceChanged()'i override et.
// TODO (7) textOptions() icinde sharedPreference'dan ilgili key bilgileri ile degerleri oku ve ata.
// TODO (8) onDestroy() icinde sharedPreferences.unregisterOnSharedPreferenceChangeListener(this) ekle.


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener, CompoundButton.OnCheckedChangeListener {

    private val txtChangeMe by lazy { findViewById<TextView>(R.id.activity_main_txtChangeMe) }
    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }
    private val chkRememberMe by lazy { findViewById<CheckBox>(R.id.activity_main_chkRememberMe) }
    private val edtUserName by lazy { findViewById<EditText>(R.id.activity_main_edtUserName) }

    private var fistTimeFlag = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initEvent()
    }

    private fun initEvent() {

        chkRememberMe.setOnCheckedChangeListener(this)

        val customSharedPreference = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        if (customSharedPreference.getString("Remembered_Key", "") != "") {

            chkRememberMe.isChecked = true
            edtUserName.setText(customSharedPreference.getString("Remembered_Key", ""))
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        textOptions()
    }

    private fun textOptions() {

        txtChangeMe.setTypeface(null, getTypeFace(sharedPreferences!!))

        val colorOption = sharedPreferences.getString(resources.getString(R.string.key_color_option), resources.getString(R.string.black_value))

        txtChangeMe.setTextColor(ContextCompat.getColor(this, getPrefColor(colorOption)))
    }

    private fun getTypeFace(sharedPreferences: SharedPreferences): Int {

        return if (sharedPreferences.getBoolean(resources.getString(R.string.key_isBold), resources.getBoolean(R.bool.def_isBold))
                && sharedPreferences.getBoolean(resources.getString(R.string.key_isItalic), resources.getBoolean(R.bool.def_isItalic))) {

            Typeface.BOLD_ITALIC

        } else if (sharedPreferences.getBoolean(getString(R.string.key_isBold), resources.getBoolean(R.bool.def_isBold))) {

            Typeface.BOLD

        } else if (sharedPreferences.getBoolean(getString(R.string.key_isItalic), resources.getBoolean(R.bool.def_isItalic))) {

            Typeface.ITALIC

        } else {

            Typeface.NORMAL
        }
    }

    private fun getPrefColor(colorKey: String): Int {

        return when (colorKey) {

            resources.getString(R.string.red_value) -> {

                R.color.red
            }

            resources.getString(R.string.blue_value) -> {

                R.color.blue
            }

            resources.getString(R.string.green_value) -> {

                R.color.green
            }

            else -> {
                R.color.black
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

        if (!fistTimeFlag) {

            val customSharedPreference = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val editor = customSharedPreference.edit()

            customSharedPreference.getInt("key", 2)

            if (isChecked) {

                editor.putString("Remembered_Key", edtUserName.text.toString())
            } else {
                editor.putString("Remembered_Key", "")
            }

            editor.apply()
            //editor.commit()
        } else {
            fistTimeFlag = false
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        textOptions()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_activity_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {

            R.id.menu_activity_main_settings -> {

                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()

        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}
