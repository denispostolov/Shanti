package com.example.shanti.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shanti.data.dao.TrainerDao
import com.example.shanti.data.entity.TrainerEntity

@Database(
    entities = [TrainerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TrainerDatabase: RoomDatabase() {
    abstract fun trainerDao(): TrainerDao?

    companion object{
        // marking the instance as volatile to ensure atomic access to the variable
        @Volatile
        private var INSTANCE: TrainerDatabase? = null

        fun getDatabase(context: Context): TrainerDatabase? {
            if (INSTANCE == null) {
                synchronized(TrainerDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = androidx.room.Room.databaseBuilder(
                            context.applicationContext,
                            TrainerDatabase::class.java, "trainer_database"
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