package com.example.sanathitunes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sanathitunes.models.Result

@Entity(tableName = "artists_table")
data class Data(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "artist") val artist: String?,
    @ColumnInfo(name = "song") val song: String?,
    @ColumnInfo(name = "image") val image: String?
)