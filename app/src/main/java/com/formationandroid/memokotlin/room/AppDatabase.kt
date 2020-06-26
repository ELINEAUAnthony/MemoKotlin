package com.formationandroid.memokotlin.room

import android.content.ContentValues
import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.formationandroid.memokotlin.memos.dao.MemoDAO
import com.formationandroid.memokotlin.memos.dao.MemoDTO

@Database(entities = arrayOf(MemoDTO::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun MemoDAO(): MemoDAO
    companion object {


        @Volatile
        private var instance: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {
            val Instance = instance
            if (Instance != null) {
                return Instance
            }

            synchronized(AppDatabase::class.java) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "memos.db"
                )
                    .allowMainThreadQueries()
                    .addCallback(addDatabase())
                    .build()
                return instance as AppDatabase
            }

        }

        private fun addDatabase(): Callback {

            return object : Callback() {

                @Override
                override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    for (i in 1..5) {
                        val contentValues = ContentValues()
                        contentValues.put("memoId", i)
                        contentValues.put("intitule", String.format("memo numéro : %s", i))
                        db.insert("memos", OnConflictStrategy.IGNORE, contentValues)
                    }
                }
            }

        }

    }
}
