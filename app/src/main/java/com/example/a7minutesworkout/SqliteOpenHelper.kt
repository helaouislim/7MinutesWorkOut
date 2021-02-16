package com.example.a7minutesworkout

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SevenMinutesWorkout.db"
        private const val TABLE_HISTORY = "history"

        private const val COLUMN_ID = "_id"
        private const val COLUMN_COMPLETED_DATE = "completed_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE " + TABLE_HISTORY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_COMPLETED_DATE + " TEXT" + ")")
        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY)
        onCreate(db)
    }

    fun addDate(date: String) {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COLUMN_COMPLETED_DATE, date) // EmpModelClass Name


        // Inserting Row
        val success = db.insert(TABLE_HISTORY, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
    }

    fun getAllCompletedDateList(): ArrayList<String> {

        val dateList: ArrayList<String> = ArrayList<String>()
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_HISTORY"
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var date: String

        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                date = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
                dateList.add(date)
            }
        }
        return dateList
    }


}