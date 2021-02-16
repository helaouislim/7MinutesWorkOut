package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_exercice.*
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)



        setSupportActionBar(toolbar_finish_activity)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        addDateToDatabase()

    }

    fun goToMain(view: View) {
        finish()
    }

    private fun addDateToDatabase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.i("Date", ""+ dateTime)

        val sdf = SimpleDateFormat("dd MMM YYY HH:mm:ss",Locale.getDefault())
        val date = sdf.format((dateTime))

        val dbHandler = SqliteOpenHelper(this,null)
        dbHandler.addDate(date)
        Log.i("Date", "date added")
    }

}