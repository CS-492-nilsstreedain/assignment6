/* Assignment 6
   Hunt.kt
   Nils Streedain | streedan@oregonstate.com
   CS 492 | Oregon State University
*/
package com.example.assignment6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment6.data.ClueData
import com.example.assignment6.util.LocationUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Hunt : ViewModel() {

    private val _currentClueIndex = MutableStateFlow(0)
    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime: StateFlow<Long> = _elapsedTime

    private val _currentClue = MutableStateFlow(ClueData.clues[0])
    val currentClue: StateFlow<com.example.assignment6.model.Clue> = _currentClue

    private var timerJob: Job? = null

    init {
        resetGame()
    }

    fun startTimer() {
        stopTimer()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                _elapsedTime.value++
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun resetGame() {
        stopTimer()
        _elapsedTime.value = 0L
        _currentClueIndex.value = 0
        _currentClue.value = ClueData.clues[0]
        startTimer() // start a fresh timer on reset
    }

    fun moveToNextClue() {
        val nextIndex = _currentClueIndex.value + 1
        if (nextIndex < ClueData.clues.size) {
            _currentClueIndex.value = nextIndex
            _currentClue.value = ClueData.clues[nextIndex]
        }
    }

    fun checkLocation(currentLat: Double, currentLon: Double, thresholdKm: Double = 0.05): Boolean {
        val clue = _currentClue.value
        val distanceKm = LocationUtils.calculateDistance(currentLat, currentLon, clue.latitude, clue.longitude)
        return distanceKm <= thresholdKm
    }
}