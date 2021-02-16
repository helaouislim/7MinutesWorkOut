package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    private var historyAdapter: HistoryStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_history_activity)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "HISTORY"
        }
        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        setupHistoryRecyclerView()
    }

    private fun getAllCompletedDate(): ArrayList<String> {
        val dbHandler = SqliteOpenHelper(this, null)
        val allCompletedDateList = dbHandler.getAllCompletedDateList()

        for (i in allCompletedDateList) {
            Log.i("DateHistoryActivity", i)
        }
        return allCompletedDateList
    }

    private fun setupHistoryRecyclerView() {
        val dataList = getAllCompletedDate()
        if (dataList.size > 0) {
            tv_history_no_data.visibility = View.GONE
            rvHistoryRow.visibility = View.VISIBLE
            rvHistoryRow.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            historyAdapter = HistoryStatusAdapter(this, dataList)
            rvHistoryRow.adapter = historyAdapter

        } else {

            tv_history_no_data.visibility = View.VISIBLE
            rvHistoryRow.visibility = View.GONE
        }
    }
}