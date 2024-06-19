package com.devx.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devx.data.dataSource.local.entity.ProfileMatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfiles(profileList: List<ProfileMatchEntity>)

    @Query("SELECT * FROM profile_match")
    fun getAllMatches(): Flow<List<ProfileMatchEntity>>

    @Query("SELECT * FROM profile_match WHERE userId = :userId")
    fun getProfileById(userId: String): ProfileMatchEntity

    @Query("UPDATE profile_match SET status = :status WHERE userId = :userId")
    fun updateStatus(
        userId: String,
        status: Int,
    )
}
