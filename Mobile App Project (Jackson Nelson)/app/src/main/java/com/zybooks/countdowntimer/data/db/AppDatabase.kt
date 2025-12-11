package com.zybooks.countdowntimer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ScheduledEvent::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduledEventDao(): ScheduledEventDao
}