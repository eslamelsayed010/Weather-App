package com.example.weatherapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.features.favorite.model.FavoriteModel
import com.example.weatherapp.features.notification.model.NotificationModel

@Database(
    entities = [
        NotificationModel::class,
        FavoriteModel::class
    ],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "weather_database"
                    )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
