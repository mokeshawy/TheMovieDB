package com.paymob.themoviedb.feature.fragments.comoun.data.room_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paymob.themoviedb.feature.fragments.comoun.data.model.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<RemoteKeyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: RemoteKeyEntity)

    @Query("select * from RemoteKey where id=:key")
    suspend fun getKeyByMovie(key: String): RemoteKeyEntity?

    @Query("delete from RemoteKey")
    suspend fun clearKeys()
}