package io.androidedu.datapersistance.ui.sql.enums


/******************************
 * Created by Gökhan ÖZTÜRK   |
 * 3.02.2018                 |
 * GokhanOzturk@AndroidEdu.IO |
 *****************************/
enum class DatabaseInfo {

    DatabaseName {
        override fun toString(): String {
            return "Guests"
        }
    },

    DatabaseVersion {
        override fun toString(): String {
            return "1"
        }
    }
}