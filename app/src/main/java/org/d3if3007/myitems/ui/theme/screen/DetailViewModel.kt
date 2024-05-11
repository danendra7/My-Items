package org.d3if3007.myitems.ui.theme.screen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3007.myitems.database.BarangDao
import org.d3if3007.myitems.model.Barang

class DetailViewModel(private val dao: BarangDao) : ViewModel() {

    fun insert(nama: String, stok: Int, harga: Int){
        val barang = Barang(
            nama = nama,
            stok = stok.toString(),
            harga = harga.toString()
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(barang)
        }
    }
    suspend fun getBarang(id: Long): Barang? {
        return dao.getBarangById(id)
    }

    fun update(id: Long, nama: String, stok: Int, harga: Int){
        val barang = Barang(
            id = id,
            nama = nama,
            stok = stok.toString(),
            harga = harga.toString()
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(barang)
        }
    }

    fun delete(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
            }
        }
}