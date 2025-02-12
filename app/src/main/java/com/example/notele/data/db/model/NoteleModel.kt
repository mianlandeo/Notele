package com.example.notele.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notele.presentation.theme.BabyBlue
import com.example.notele.presentation.theme.LightGreen
import com.example.notele.presentation.theme.RedOrange
import com.example.notele.presentation.theme.RedPink
import com.example.notele.presentation.theme.Violet
import javax.annotation.processing.Generated

@Entity(tableName = "notele_table")
data class NoteleModel(

    @PrimaryKey val idNotele: Int? = null,
    val title: String,
    val description: String,
    val time: Long,
    val color : Int
) {
    companion object {
        val notelePriority = listOf(
            RedOrange, RedPink,
            BabyBlue, Violet, LightGreen
        )
    }
}

class MessageException(message: String) :Exception(message)