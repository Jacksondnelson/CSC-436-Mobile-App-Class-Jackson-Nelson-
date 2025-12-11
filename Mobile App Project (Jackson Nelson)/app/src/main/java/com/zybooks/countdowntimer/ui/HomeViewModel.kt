package com.zybooks.countdowntimer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    // Map day -> activities list
    private val _activitiesByDay = MutableStateFlow<Map<String, List<String>>>(
        mapOf(
            "Sunday" to emptyList(),
            "Monday" to emptyList(),
            "Tuesday" to emptyList(),
            "Wednesday" to emptyList(),
            "Thursday" to emptyList(),
            "Friday" to emptyList(),
            "Saturday" to emptyList()
        )
    )

    //put any functions you need to access your data base here
    val activitiesByDay: StateFlow<Map<String, List<String>>> = _activitiesByDay

}