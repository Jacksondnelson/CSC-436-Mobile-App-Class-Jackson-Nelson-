package com.zybooks.countdowntimer.data

import com.example.countdowntimer.data.db.ScheduledEvent
import com.example.countdowntimer.data.db.ScheduledEventDao

class EventRepository(
    private val dao: ScheduledEventDao
) {
    suspend fun getEventsForDay(day: String): List<ScheduledEvent> {
        return dao.getEventsForDay(day)
    }

    suspend fun addEvent(event: ScheduledEvent) {
        dao.insertEvent(event)
    }

    suspend fun deleteEvent(event: ScheduledEvent) {
        dao.deleteEvent(event)
    }
}