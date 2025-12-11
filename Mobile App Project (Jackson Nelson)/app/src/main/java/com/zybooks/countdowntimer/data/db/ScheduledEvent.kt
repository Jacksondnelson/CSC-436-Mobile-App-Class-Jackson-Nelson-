package com.zybooks.countdowntimer.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scheduled_events")
data class ScheduledEvent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayOfWeek: String,
    val taskName: String,
    //change start and end time types if neccesary
    val startTime: String,
    val endTime: String
)
