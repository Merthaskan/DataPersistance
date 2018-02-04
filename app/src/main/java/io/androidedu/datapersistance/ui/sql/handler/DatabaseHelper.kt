package io.androidedu.datapersistance.ui.sql.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.TextUtils
import io.androidedu.datapersistance.ui.sql.enums.DatabaseInfo
import io.androidedu.datapersistance.ui.sql.model.GuestInfo


/******************************
 * Created by Gökhan ÖZTÜRK   |
 * 3.02.2018                 |
 * GokhanOzturk@AndroidEdu.IO |
 *****************************/

class DatabaseHelper(context: Context)

    : SQLiteOpenHelper(context, DatabaseInfo.DatabaseName.toString(), null, DatabaseInfo.DatabaseVersion.toString().toInt()) {

    private val TABLE_GUESTS = "guests"

    private val KEY_ID = "id"
    private val KEY_NAME = "name"
    private val KEY_SURNAME = "surname"
    private val KEY_PHONE = "phone"

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE $TABLE_GUESTS " +
                "($KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_NAME TEXT," +
                "$KEY_SURNAME TEXT," +
                "$KEY_PHONE TEXT)"

        db!!.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_GUESTS")
        onCreate(db)
    }

    fun addGuest(guestInfo: GuestInfo) {

        val db = writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, guestInfo.guestName)
        contentValues.put(KEY_SURNAME, guestInfo.guestSurname)
        contentValues.put(KEY_PHONE, guestInfo.guestPhone)

        db.insert(TABLE_GUESTS, null, contentValues)
        db.close()
    }

    fun updateGuest(guestInfo: GuestInfo): Int {

        val db = writableDatabase

        val contentValues = ContentValues()

        if (!TextUtils.isEmpty(guestInfo.guestName.trim()))
            contentValues.put(KEY_NAME, guestInfo.guestName)

        if (!TextUtils.isEmpty(guestInfo.guestSurname.trim()))
            contentValues.put(KEY_SURNAME, guestInfo.guestSurname)

        if (!TextUtils.isEmpty(guestInfo.guestPhone.trim()))
            contentValues.put(KEY_PHONE, guestInfo.guestPhone)

        val isSuccess = db.update(TABLE_GUESTS, contentValues, "$KEY_ID = ?", arrayOf(guestInfo.guestID.toString()))

        db.close()

        return isSuccess
    }

    fun deleteGuest(guestInfo: GuestInfo) {

        val db = writableDatabase

        db.delete(TABLE_GUESTS, "$KEY_ID = ?", arrayOf(guestInfo.guestID.toString()))
        db.close()
    }

    fun getAllGuests(): ArrayList<GuestInfo> {

        val db = readableDatabase

        val guestInfoList = ArrayList<GuestInfo>()

        val selectQuery = "SELECT  * FROM $TABLE_GUESTS"

        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {

            do {

                val guestInfo = GuestInfo(cursor.getString(0).toLong(),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3))

                guestInfoList.add(guestInfo)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return guestInfoList
    }

    fun getGuest(guestID: Long): GuestInfo {

        val db = this.readableDatabase

        val cursor = db.query(TABLE_GUESTS, arrayOf(KEY_ID, KEY_NAME, KEY_SURNAME, KEY_PHONE),
                "$KEY_ID = ?", arrayOf(guestID.toString()), null, null, null, null)

//        if (cursor != null)
//            cursor.moveToFirst()
        cursor?.moveToFirst()

        val guestInfo = GuestInfo(cursor.getString(0).toLong(),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3))

        cursor.close()
        db.close()

        return guestInfo
    }
}