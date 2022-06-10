package com.shoppi.app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "schedule")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "namelabel")
    var namelabel: String,

    @ColumnInfo(name = "timerange")
    var timerange: String
) {
    constructor() : this(null, "", "")
}