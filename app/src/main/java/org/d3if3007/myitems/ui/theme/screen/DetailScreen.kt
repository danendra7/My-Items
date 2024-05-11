package org.d3if3007.myitems.ui.theme.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3007.myitems.ui.theme.MyItemsTheme
import org.d3if3007.myitems.R
import org.d3if3007.myitems.database.BarangDb
import org.d3if3007.myitems.util.ViewModelFactory

const val KEY_ID_BARANG = "idBarang"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val context = LocalContext.current
    val db = BarangDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember {
        mutableStateOf("")
    }
    var namaError by remember { mutableStateOf(false) }

    var stok by remember {
        mutableStateOf("")
    }
    var stokError by remember { mutableStateOf(false) }

    var harga by remember {
        mutableStateOf("")
    }
    var hargaError by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }


    LaunchedEffect(true){
        if (id == null) return@LaunchedEffect
        val data = viewModel.getBarang(id) ?: return@LaunchedEffect
        nama = data.nama
        stok = data.stok
        harga = data.harga
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_barang))
                    else
                        Text(text = stringResource(id = R.string.edit_barang))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        namaError = nama == ""
                        stokError = (stok == "0" || stok == "")
                        hargaError = (harga == "0" || stok == "")
                        if (namaError || stokError || hargaError){
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null){
                            viewModel.insert(nama, stok.toInt() , harga.toInt())
                        } else {
                            viewModel.update(id, nama, stok.toInt(), harga.toInt())
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(id = R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction { showDialog = true }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }
                        ) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ){ padding ->
        FormBarang(
            nama = nama,
            onNamaChange = {nama = it},
            stok = stok,
            onStokChange = {stok = it},
            modifier = Modifier.padding(padding),
            harga = harga,
            onHargaChange ={harga = it}
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus_barang))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormBarang(
    nama: String, onNamaChange: (String) -> Unit,
    stok: String, onStokChange: (String) -> Unit,
    harga: String, onHargaChange: (String) -> Unit,
    modifier: Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = {onNamaChange(it) },
            label = { Text(text = stringResource(id = R.string.nama))},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = stok,
            onValueChange = {onStokChange(it) },
            label = { Text(text = stringResource(id = R.string.stok))},
            keyboardOptions = KeyboardOptions(
               keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = harga,
            onValueChange = {onHargaChange(it) },
            label = {Text(text = stringResource(id = R.string.harga))},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    MyItemsTheme {
        DetailScreen(rememberNavController())
        }
}