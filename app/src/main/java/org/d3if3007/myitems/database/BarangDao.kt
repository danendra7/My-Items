package org.d3if3007.myitems.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3007.myitems.model.Barang

@Dao
interface BarangDao {
    @Insert
    suspend fun insert(listBarang: Barang)

    @Update
    suspend fun update(listBarang: Barang)

    @Query("SELECT * FROM listBarang ORDER BY harga, stok")
    fun getBarang(): Flow<List<Barang>>

    @Query("SELECT * FROM listBarang WHERE id = :id")
    suspend fun getBarangById(id: Long): Barang?

    @Query("DELETE FROM listBarang WHERE id = :id")
    suspend fun deleteById(id: Long)
}