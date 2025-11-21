package com.zoe.wan.android.example.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.zoe.wan.android.example.repository.data.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    /**
     * @Upsert等价于@Insert(onConflict = OnConflictStrategy.REPLACE)
     */
    @Upsert
    suspend fun insert(user: User): Long

    @Upsert
    suspend fun upsert(items: List<User>): List<Long>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(items: List<User>): List<Long>

    @Query("SELECT * FROM users")
    fun getAllUsersLiveData(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE age > :minAge")
    fun getUsersOlderThanLiveData(minAge: Int): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserByIdLiveData(userId: Long): LiveData<User?>

    @Query("SELECT * FROM users")
    fun getAllUsersFlow(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE age > :minAge")
    fun getUsersOlderThanFlow(minAge: Int): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserByIdFlow(userId: Long): Flow<User>

    // 返回 Flow 并指定调度器
    @Query("SELECT * FROM users WHERE name LIKE :searchQuery")
    fun searchUsersFlow(searchQuery: String): Flow<List<User>>
}