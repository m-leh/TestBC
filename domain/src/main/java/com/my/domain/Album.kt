package com.my.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Album(@PrimaryKey var id: Long, var albumId: Int, var title: String?, var url: String?)