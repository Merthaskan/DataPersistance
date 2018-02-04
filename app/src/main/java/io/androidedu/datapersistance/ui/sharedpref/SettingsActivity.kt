package io.androidedu.datapersistance.ui.sharedpref

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.androidedu.datapersistance.R

// TODO (1) Geri donus butonunu handle edebilmek icin, supportActionBar'i kontrol et. ActionBar varsa geributonu icin setDisplayHomeAsUpEnabled(true) yap.
// TODO (2) onOptionsItemSelected() override et ve R.id.home case'i icin NavUtils.navigateUpFromSameTask(this) calistir.
// TODO (3) addSettingsFragment() methodu ile SettingsFragment'i ekle.

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        if (supportActionBar != null) {

            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        addSettingsFragment()
    }

    private fun addSettingsFragment() {


        supportFragmentManager.beginTransaction().add(R.id.activity_settings_frmSettingsFragment, SettingsFragment()).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {

            R.id.home -> {

                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
