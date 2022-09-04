package com.weathery.weather.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.weathery.weather.model.AlertData
import com.weathery.weather.model.DataResponse
import com.weathery.weather.model.Favourite
import kotlinx.coroutines.flow.Flow


@Dao
interface RoomDao {



        ///****** weather table *********//
        @Query("SELECT * FROM weather_table")
        suspend fun getAlphabetizedWords(): List<DataResponse>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(word: DataResponse)

        @Query("DELETE FROM weather_table")
        suspend fun deleteAll()

        @Query("SELECT * FROM weather_table WHERE timezone =:timezone")
        suspend fun getWeatherByTimeZone(timezone: String):DataResponse

        @Query("DELETE FROM weather_table")
        suspend fun deleteWeather()

        ////////////////////////////////////////////////////////////////////////////////////

        ///****** favourite table *********//


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun addFavourite(fav: Favourite)

        @Delete
        suspend fun deleteFavourite(fav: Favourite)

        @Query("SELECT * FROM favorite WHERE name = :name")
        suspend fun getFavoriteByName(name:String):Favourite


        @Query("SELECT * FROM favorite")
        fun getAllFavourite():LiveData<List<Favourite>>

        @Update
        suspend fun updateFavourite(fav: Favourite)

        //**********alerts************//

        @Insert
        suspend fun addAlert(alert: AlertData)

        @Delete
        suspend fun deleteAlert(alert: AlertData)


      /*  @Query("SELECT * FROM alertdata")
        fun getAllAlerts():LiveData<List<AlertData>>*/

        @Query("SELECT * FROM alertdata")
        fun getAllAlerts(): Flow<List<AlertData>>

}