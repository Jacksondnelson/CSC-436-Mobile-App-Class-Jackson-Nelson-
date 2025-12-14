package com.zybooks.countdowntimer.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduledEventDao {

   /* @Query("SELECT * FROM scheduled_events WHERE dayOfWeek = :day")
    suspend fun getEventsForDay(day: String): List<ScheduledEvent>

    @Insert
    suspend fun insertEvent(event: ScheduledEvent)

    @Delete
    suspend fun deleteEvent(event: ScheduledEvent)*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: ScheduledEvent)

    @Delete
    suspend fun deleteEvent(event: ScheduledEvent)

    @Query("SELECT * FROM scheduled_events WHERE dayOfWeek = :day ORDER BY startTimeMillis ASC")
    fun getEventsForDay(day: String): Flow<List<ScheduledEvent>>
}