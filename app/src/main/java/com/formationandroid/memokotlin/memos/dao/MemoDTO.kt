package com.formationandroid.memokotlin.memos.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memos")
class MemoDTO (intitule: String?) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "memoId")
    var memoId: Long = 0

    @JvmField
    @ColumnInfo(name = "intitule")
    var intitule: String? = null

    init {
        this.intitule = intitule
    }

}