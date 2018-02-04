package io.androidedu.datapersistance.ui.sql

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import io.androidedu.datapersistance.R
import io.androidedu.datapersistance.ui.sql.adapter.GuestListAdapter
import io.androidedu.datapersistance.ui.sql.handler.DatabaseHelper
import io.androidedu.datapersistance.ui.sql.interfaces.CustomAdapterClickListener
import io.androidedu.datapersistance.ui.sql.model.GuestInfo

class DashboardActivity : AppCompatActivity(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, CustomAdapterClickListener {

    private val swipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.activity_dashboard_swipeRefreshLayout) }
    private val recycGuestList by lazy { findViewById<RecyclerView>(R.id.activity_dashboard_recycGuestList) }

    private val addDialog by lazy { AlertDialog.Builder(this).create() }
    private val btnSave by lazy { addDialog.findViewById<Button>(R.id.custom_alert_dialog_add_guest_btnSave) }
    private val edtGuestName by lazy { addDialog.findViewById<EditText>(R.id.custom_alert_dialog_add_guest_edtGuestName) }
    private val edtGuestSurname by lazy { addDialog.findViewById<EditText>(R.id.custom_alert_dialog_add_guest_edtGuestSurname) }
    private val edtGuestPhone by lazy { addDialog.findViewById<EditText>(R.id.custom_alert_dialog_add_guest_edtGuestPhone) }

    private val databaseHelper by lazy { DatabaseHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initEvent()
    }

    private fun initEvent() {

        swipeRefreshLayout.setOnRefreshListener(this)

        val linearLayoutManager = LinearLayoutManager(this)
        val guestListAdapter = GuestListAdapter(databaseHelper.getAllGuests(), this)

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                val deletedGuestInfo = GuestInfo(viewHolder.itemView.tag.toString().toLong(), "", "", "")
                databaseHelper.deleteGuest(deletedGuestInfo)

                (recycGuestList.adapter as GuestListAdapter).setGuestList(databaseHelper.getAllGuests())
                recycGuestList.adapter.notifyDataSetChanged()
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)

        recycGuestList.layoutManager = linearLayoutManager
        recycGuestList.adapter = guestListAdapter
        itemTouchHelper.attachToRecyclerView(recycGuestList)
    }

    private fun createAlertDialog(buttonText: String
                                  , guestKey: Long = 0L
                                  , guestName: String = ""
                                  , guestSurname: String = ""
                                  , guestPhone: String = "") {

        val layoutInflater = LayoutInflater.from(this)
        val alertView = layoutInflater.inflate(R.layout.custom_alert_dialog_add_guest, null, false)

        addDialog.setView(alertView)
        addDialog.show()

        btnSave?.text = buttonText
        edtGuestName?.setText(guestName)
        edtGuestSurname?.setText(guestSurname)
        edtGuestPhone?.setText(guestPhone)

        btnSave?.tag = guestKey
        btnSave?.setOnClickListener(this)
    }

    private fun eventSaveButton() {

        val guestInfo = GuestInfo(guestName = edtGuestName?.text.toString()
                , guestSurname = edtGuestSurname?.text.toString()
                , guestPhone = edtGuestPhone?.text.toString())

        databaseHelper.addGuest(guestInfo)
        addDialog.dismiss()
    }

    private fun eventUpdateButton(updateButton: Button) {

        val guestInfo = GuestInfo(guestID = updateButton.tag.toString().toLong()
                , guestName = edtGuestName?.text.toString()
                , guestSurname = edtGuestSurname?.text.toString()
                , guestPhone = edtGuestPhone?.text.toString())

        databaseHelper.updateGuest(guestInfo)
        addDialog.dismiss()
    }

    override fun onClick(view: View?) {

        when ((view as Button).id) {

            R.id.custom_alert_dialog_add_guest_btnSave -> {

                if ((view).text.toString() == resources.getString(R.string.save)) {

                    eventSaveButton()

                } else if ((view).text.toString() == resources.getString(R.string.update)) {

                    eventUpdateButton(view)
                }
            }
        }

    }

    override fun onCustomItemClickListener(guestInfo: GuestInfo, position: Int) {

        createAlertDialog(resources.getString(R.string.update),
                guestInfo.guestID,
                guestInfo.guestName,
                guestInfo.guestSurname,
                guestInfo.guestPhone)
    }

    override fun onRefresh() {

        (recycGuestList.adapter as GuestListAdapter).setGuestList(databaseHelper.getAllGuests())
        recycGuestList.adapter.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_activity_dashboard, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {

            R.id.menu_activity_dashboard_add_guest -> {

                createAlertDialog(resources.getString(R.string.save))
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
