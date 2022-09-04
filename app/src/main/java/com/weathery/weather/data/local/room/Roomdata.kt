package com.weathery.weather.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.weathery.weather.data.local.room.converters.*
import com.weathery.weather.model.DataResponse
import com.weathery.weather.model.Favourite
import com.weathery.weather.model.AlertData


@Database(entities = arrayOf(DataResponse::class, Favourite::class,AlertData::class), version = 1)
@TypeConverters(DailyConverter::class, HourlyConverter::class, CurrentConverter::class, CalenderConverter::class, AlertConverter::class)
public abstract class Roomdata :RoomDatabase(){

   abstract fun roomDao(): RoomDao

            companion object {
            // Singleton prevents multiple instances of database opening at the
            // same time.
           // @Volatile
            private var INSTANCE: Roomdata? = null

            fun getDatabase(context: Context): Roomdata {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE
                        ?: synchronized(this) {
                    val instance = Room.databaseBuilder(context.applicationContext, Roomdata::class.java, "weather table").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
            }
        }
}



