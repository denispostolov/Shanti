package com.example.shanti.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shanti.data.dao.SessionDao
import com.example.shanti.data.entity.SessionEntity

@Database(
    entities = [SessionEntity::class],
    version = 6,
    exportSchema = false
)
abstract class SessionDatabase: RoomDatabase() {
    abstract fun sessionDao(): SessionDao?

    companion object {
        // marking the instance as volatile to ensure atomic access to the variable
        @Volatile
        private var INSTANCE: SessionDatabase? = null

        fun getDatabase(context: Context): SessionDatabase? {
            if (INSTANCE == null) {
                synchronized(SessionDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = androidx.room.Room.databaseBuilder(
                            context.applicationContext,
                            SessionDatabase::class.java, "session_database"
                        )
                            // how to add a migration
                            .addMigrations(

                            )
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            .fallbackToDestructiveMigration()
                            .addCallback(roomDatabaseCallback)
                            .build()

                    }
                }
            }
            return INSTANCE
        }

        private val roomDatabaseCallback: Callback =
            object : Callback() {
            }
    }
}