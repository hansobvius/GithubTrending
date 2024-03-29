package com.android.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.cache.db.ConfigConstants

@Entity(tableName = ConfigConstants.TABLE_NAME)
data class Config(
    @PrimaryKey(autoGenerate = true)
    var id: Int = -1,
    var lastCacheTime: Long)