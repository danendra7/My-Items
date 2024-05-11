package org.d3if3007.myitems.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listBarang")
data class Barang(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val stok: String,
    val harga: String,
)
