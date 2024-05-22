package com.example.shanti.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shanti.data.dao.SessionDao
import com.example.shanti.data.dao.TrainerDao
import com.example.shanti.data.entity.SessionEntity

@Database(
    entities = [SessionEntity::class],
    version = 3,
    exportSchema = false
)
abstract class SessionDatabase: RoomDatabase() {
    abstract fun sessionDao(): SessionDao?

    companion object{
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

        /** TODO:
         * Override the onOpen method to populate the database.
         * For this sample, we clear the database every time it is created or opened.
         * If you want to populate the database only when the database is created for the 1st time,
         * override MyRoomDatabase.Callback()#onCreate
         */
        private val roomDatabaseCallback: Callback =
            object : Callback() {
            }
    }
}