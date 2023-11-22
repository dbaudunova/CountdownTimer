package com.example.countdowntimer

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private lateinit var timer: CountDownTimer
    var timerValue = MutableLiveData<Long>()

    private val _seconds = MutableLiveData<Int>()
    fun seconds(): LiveData<Int> {
        return _seconds
    }

    private val _finished = MutableLiveData<Boolean>()
    fun finished(): LiveData<Boolean> {
        return _finished
    }

    private var timeRemaining: Long = 0
    private var isTimerRunning = false

    fun startTimer() {
        if (!isTimerRunning) {
            timer = object : CountDownTimer(timerValue.value!!.toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeRemaining = millisUntilFinished
                    val timeLeft = millisUntilFinished / 1000 //перевод из милисекунд в секунды
                    _seconds.value = timeLeft.toInt()
                }

                override fun onFinish() {
                    _finished.value = true
                }
            }.start()
            isTimerRunning = true
        }
    }

    fun stopTimer() {
        timer.cancel()
        isTimerRunning = false
    }

    fun resumeTimer() {
        if (!isTimerRunning) {
            startTimerWithRemainingTime(timeRemaining)
        }
    }

    private fun startTimerWithRemainingTime(remainingTime: Long) {
        timer = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                _seconds.value = timeLeft.toInt()
            }

            override fun onFinish() {
                _finished.value = true
            }
        }.start()
        isTimerRunning = true
    }

    override fun onCleared() {
        super.onCleared()
        if (isTimerRunning) {
            timer.cancel()
        }
    }
}