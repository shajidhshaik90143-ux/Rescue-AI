package com.example.rescueai.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EmergencyDao {
    @Query("SELECT * FROM emergency_history ORDER BY timestamp DESC")
    fun getAllEmergencies(): Flow<List<EmergencyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmergency(emergency: EmergencyEntity)

    @Query("DELETE FROM emergency_history")
    suspend fun clearHistory()
}
