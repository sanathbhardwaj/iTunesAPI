package com.example.sanathitunes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sanathitunes.models.Result

@Dao
interface ResultDao {
//        @Query("SELECT * FROM Result")
//        fun getAll(): List<Data>

        @Query("SELECT * FROM artists_table")
        suspend fun loadAllBySearchResult(): List<Data>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insertBySearchResult(vararg results: Data)

//        @Delete
//        fun delete(user: Data)
}