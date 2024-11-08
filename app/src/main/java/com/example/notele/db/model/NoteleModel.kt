package com.example.notele.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notele.ui.theme.DisabledPriority
import com.example.notele.ui.theme.GreenPriority
import com.example.notele.ui.theme.RedPriority
import com.example.notele.ui.theme.YellowPriority

@Entity(tableName = "notele_table")
data class NoteleModel (

    @PrimaryKey val idNotele: Int,
    val title: String,
    val description: String,
    val date: String,
    val priority: String
) {
    companion object {
        val notelePriority = listOf(DisabledPriority, RedPriority,
            YellowPriority, GreenPriority)
    }
}