package com.example.rescueai.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmergencyEntity::class], version = 1, exportSchema = false)
abstract class RescueDatabase : RoomDatabase() {
    abstract fun emergencyDao(): EmergencyDao

    companion object {
        @Volatile
        private var INSTANCE: RescueDatabase? = null

        fun getDatabase(context: Context): RescueDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RescueDatabase::class.java,
                    "rescue_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
