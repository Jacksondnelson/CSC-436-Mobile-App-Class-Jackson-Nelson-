package com.zybooks.countdowntimer.ui
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.countdowntimer.data.db.AppDatabase
import com.zybooks.countdowntimer.data.db.ScheduledEvent
import com.zybooks.countdowntimer.data.db.ScheduledEventDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/*class DayActivitiesViewModel : ViewModel() {
    // TODO: Implement state and logic for day-specific activities
    //add and remove activites functions should be added here

    //fun addActivity(day: String, activity: String) { /*...*/ }
    fun addActivity(
        taskName: String,
        startTime: Int,
        endTime: Int
    ) {
        val event = ScheduledEvent(
            day = currentDay,
            taskName = taskName,
            startTime = startTime,
            endTime = endTime
        )

        viewModelScope.launch {

            // 1. Add new event into the database
            com.zybooks.countdowntimer.data.db.ScheduledEventDao().insertEvent(event)

            // 2. Re-load all events for the current day
            val updatedList = db.scheduledEventDao().getEventsForDay(currentDay)

            // 3. Sorting by startTime (Int → already sorted correctly)
            _activities.value = updatedList.sortedBy { it.startTime }
        }
    }
   fun removeActivity(day: String, activity: String) { /*...*/ }
}*/

/*class DayActivitiesViewModel(
    private val db: AppDatabase
) : ViewModel() {

    private val _activities = MutableStateFlow<List<ScheduledEvent>>(emptyList())
    val activities: StateFlow<List<ScheduledEvent>> get() = _activities

    private var currentDay: String = ""

    fun loadActivitiesForDay(day: String) {
        currentDay = day
        viewModelScope.launch {
            _activities.value = db.scheduledEventDao().getEventsForDay(day)
        }
    }

    fun addActivity(taskName: String, startTime: Int, endTime: Int) {
        val event = ScheduledEvent(
            day = currentDay,
            taskName = taskName,
            startTime = startTime,
            endTime = endTime
        )

        viewModelScope.launch {
            db.scheduledEventDao().insertEvent(event)
            val updatedList = db.scheduledEventDao().getEventsForDay(currentDay)
            _activities.value = updatedList.sortedBy { it.startTime }
        }
    }

    fun removeActivity(event: ScheduledEvent) {
        viewModelScope.launch {
            db.scheduledEventDao().deleteEvent(event)
            val updatedList = db.scheduledEventDao().getEventsForDay(currentDay)
            _activities.value = updatedList.sortedBy { it.startTime }
        }
    }
}*/
class DayActivitiesViewModel(
    private val dao: ScheduledEventDao
) : ViewModel() {

    var activityList by mutableStateOf<List<ScheduledEvent>>(emptyList())
        private set

    fun loadDay(day: String) {
        viewModelScope.launch {
            dao.getEventsForDay(day).collect { events ->
                activityList = events.sortedBy { it.startTimeMillis }
            }
        }
    }

    fun addActivity(
        dayOfWeek: String,
        taskName: String,
        startHour: Int,
        startMin: Int,
        startSec: Int,
        endHour: Int,
        endMin: Int,
        endSec: Int
    ) {
        // Convert to millis (same as timer)
        val startMillis = (startHour * 3600 + startMin * 60 + startSec) * 1000L
        val endMillis = (endHour * 3600 + endMin * 60 + endSec) * 1000L
        val addedMillis = getCurrentTimeMillis() //getting the current time

        val newEvent = ScheduledEvent(
            dayOfWeek = dayOfWeek,
            taskName = taskName,
            startTimeMillis = startMillis,
            endTimeMillis = endMillis
        )

        viewModelScope.launch {
            dao.insertEvent(newEvent)

            // update on-screen list
            activityList = (activityList + newEvent).sortedBy { it.startTimeMillis }
        }
    }

    fun removeActivity(event: ScheduledEvent) {
        viewModelScope.launch {
            dao.deleteEvent(event)

            // update on-screen list
            activityList = activityList.filter { it.id != event.id }
        }
    }

    /*private fun getCurrentTimeMillis(): Long {
        val now = java.time.LocalTime.now()
        return (now.hour * 3600 + now.minute * 60 + now.second) * 1000L
    }*/
    private fun getCurrentTimeMillis(): Long {
        val calendar = java.util.Calendar.getInstance()
        val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY) // 0-23
        val minute = calendar.get(java.util.Calendar.MINUTE)    // 0-59
        val second = calendar.get(java.util.Calendar.SECOND)    // 0-59

        return (hour * 3600 + minute * 60 + second) * 1000L
    }

}
