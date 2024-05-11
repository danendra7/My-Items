package org.d3if3007.myitems.navigation

import org.d3if3007.myitems.ui.theme.screen.KEY_ID_BARANG

sealed class Screen (val route: String){
    data object Home: Screen("mainscreen")
    data object FormBaru: Screen("detailscreen")
    data object FormUbah: Screen("detailscreen/{$KEY_ID_BARANG}"){
        fun withId(id: Long) = "detailScreen/$id"
    }
}