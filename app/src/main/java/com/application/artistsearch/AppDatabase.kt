package com.example.sanathitunes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sanathitunes.models.Result

@Database(entities = arrayOf(Data::class), version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun resultsDao(): ResultDao
}
