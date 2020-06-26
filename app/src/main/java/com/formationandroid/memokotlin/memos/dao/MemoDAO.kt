package com.formationandroid.memokotlin.memos.dao

import androidx.room.*

@Dao
 interface MemoDAO {

    @Query("SELECT * FROM memos")
     fun getMemo(): List<MemoDTO>

    @Query("SELECT COUNT(*) FROM memos WHERE intitule = :intitule")
     fun countMemoParIntitule(intitule: String?): Long

    @Insert
     fun insert(vararg memos: MemoDTO)

    @Update
     fun update(vararg memos: MemoDTO)

    @Delete
     fun delete(vararg memos: MemoDTO)
}