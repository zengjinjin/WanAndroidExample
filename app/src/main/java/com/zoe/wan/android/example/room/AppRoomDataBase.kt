package com.zoe.wan.android.example.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zoe.wan.android.example.DjjApp
import com.zoe.wan.android.example.repository.data.User
import com.zoe.wan.android.example.room.dao.UserDao

@Database(
    entities = [User::class],
    version = 3,
    exportSchema = false
)
abstract class AppRoomDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        private const val DATABASE_NAME = "wan_app.db"

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 添加新列
                database.execSQL("ALTER TABLE users ADD COLUMN phone TEXT")
            }
        }
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 添加新列
                database.execSQL("ALTER TABLE users ADD COLUMN phone TEXT")
            }
        }

        /**
         * 创建线程安全的数据库实例，在延迟加载时会初始化，节省内存浪费分配
         */
        val databaseInstance: AppRoomDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(
                DjjApp.INSTANCE.applicationContext,
                AppRoomDataBase::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries() //允许在主线程操作数据库
                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
        }
    }
}