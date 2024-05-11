package org.d3if3007.myitems.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3007.myitems.database.BarangDao
import org.d3if3007.myitems.model.Barang

class MainViewModel(dao: BarangDao) : ViewModel(){
    val data: StateFlow<List<Barang>> = dao.getBarang().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
        )
}