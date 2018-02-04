package io.androidedu.datapersistance.ui.sql.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import io.androidedu.datapersistance.R


/******************************
 * Created by Gökhan ÖZTÜRK   |
 * 4.02.2018                 |
 * GokhanOzturk@AndroidEdu.IO |
 *****************************/

class GuestListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val txtGuestName by lazy { itemView.findViewById<TextView>(R.id.adapter_item_guest_list_txtGuestName) }
    val txtGuestSurname by lazy { itemView.findViewById<TextView>(R.id.adapter_item_guest_list_txtGuestSurname) }
    val txtGuestPhone by lazy { itemView.findViewById<TextView>(R.id.adapter_item_guest_list_txtGuestPhone) }
}