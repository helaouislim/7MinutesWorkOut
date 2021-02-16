package com.example.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercice.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimerDuration: Long = 10 // in sec
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30 // in sec
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercice)


        setSupportActionBar(toolbar_exercise_activity)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        tts = TextToSpeech(this, this)
        exerciseList = ExercisesConstants.defaultExerciseList()
        setUpRestView()
        setupExerciseStatusRecyclerView()

    }

    override fun onDestroy() {
        if (restTimer != null)
            restTimer!!.cancel()
        restProgress = 0
        if (exerciseTimer != null)
            exerciseTimer!!.cancel()
        exerciseProgress = 0
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if (player != null) {
            player!!.stop()
        }
        super.onDestroy()
    }

    private fun setUpRestView() {
        try {
            val soundUri =
                Uri.parse("android:resource://com.example.a7minutesworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (exerciseList != null) {
            tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()
            val speak =
                "Now rest 10 second, the next exercise is ${exerciseList!![currentExercisePosition + 1].getName()} "
            speakOut(speak)
        }
        if (restTimer != null)
            restTimer!!.cancel()
        restProgress = 0
        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE
        setRestProgressBar()
    }

    private fun setRestProgressBar() {
        progressBar.progress = restProgress

        restTimer = object : CountDownTimer(restTimerDuration * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString() // current progress
                //tvTimer.text = (pauseOffset/1000).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }
        }.start()
    }


    private fun setUpExerciseView() {
        if (exerciseTimer != null)
            exerciseTimer!!.cancel()
        exerciseProgress = 0
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE
        setExerciseProgressBar()
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }


    private fun setExerciseProgressBar() {
        exerciseProgressBar.progress = exerciseProgress
        val speak =
            "${exerciseList!![currentExercisePosition].getName()} for 30 second , you can do it "
        speakOut(speak)
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                exerciseProgressBar.progress = 30 - exerciseProgress
                tvExerciseTimer.text = (30 - exerciseProgress).toString() // current progress
                //tvTimer.text = (pauseOffset/1000).toString()
            }

            override fun onFinish() {

              //  if (currentExercisePosition < 2) {
                if (currentExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS", "The language specified is not supported")
        } else {
            Log.e("TTS", "Initialisation failed")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setupExerciseStatusRecyclerView() {
        rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(this, exerciseList!!)
        rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        customDialog.tvYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }
}

