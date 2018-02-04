package io.androidedu.datapersistance.ui.sql.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.androidedu.datapersistance.R
import io.androidedu.datapersistance.ui.sql.interfaces.CustomAdapterClickListener
import io.androidedu.datapersistance.ui.sql.model.GuestInfo


/******************************
 * Created by Gökhan ÖZTÜRK   |
 * 4.02.2018                 |
 * GokhanOzturk@AndroidEdu.IO |
 *****************************/

class GuestListAdapter(private var guestList: ArrayList<GuestInfo>, private val customAdapterClickListener: CustomAdapterClickListener)

    : RecyclerView.Adapter<GuestListViewHolder>() {

    fun setGuestList(guestList: ArrayList<GuestInfo>) {

        this.guestList = guestList
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GuestListViewHolder {

        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_item_guest_list, parent, false)
        return GuestListViewHolder(itemView)
    }

    override fun getItemCount(): Int = guestList.size

    override fun onBindViewHolder(holder: GuestListViewHolder?, position: Int) {

        val guestInfo = guestList[position]

        holder?.txtGuestName?.text = guestInfo.guestName
        holder?.txtGuestSurname?.text = guestInfo.guestSurname
        holder?.txtGuestPhone?.text = guestInfo.guestPhone

        holder?.itemView?.tag = guestInfo.guestID
        holder?.itemView?.setOnClickListener { customAdapterClickListener.onCustomItemClickListener(guestInfo, position) }
    }
}