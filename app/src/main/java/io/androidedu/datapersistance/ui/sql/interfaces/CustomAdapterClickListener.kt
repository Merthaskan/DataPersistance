package io.androidedu.datapersistance.ui.sql.interfaces

import io.androidedu.datapersistance.ui.sql.model.GuestInfo


/******************************
 * Created by Gökhan ÖZTÜRK   |
 * 4.02.2018                 |
 * GokhanOzturk@AndroidEdu.IO |
 *****************************/
interface CustomAdapterClickListener {

    fun onCustomItemClickListener(guestInfo: GuestInfo, position: Int)
}