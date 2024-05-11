package org.d3if3007.myitems.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3007.myitems.database.BarangDao
import org.d3if3007.myitems.ui.theme.screen.DetailViewModel
import org.d3if3007.myitems.ui.theme.screen.MainViewModel

class ViewModelFactory(
    private val dao: BarangDao
) : ViewModelProvider.Factory {
    @Suppress("unchceked_cast", "UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
        }
}