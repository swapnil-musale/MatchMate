package com.devx.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devx.data.dataSource.local.dao.ProfileDao
import com.devx.data.dataSource.local.entity.ProfileMatchEntity

@Database(
    version = 1,
    exportSchema = true,
    entities = [ProfileMatchEntity::class],
)
abstract class ProfileDatabase : RoomDatabase() {
    abstract val profileDao: ProfileDao
}
