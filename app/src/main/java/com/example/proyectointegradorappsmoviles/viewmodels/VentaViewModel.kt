package com.example.proyectointegradorappsmoviles.viewmodels

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.proyectointegradorappsmoviles.databases.HorusDatabase
import com.example.proyectointegradorappsmoviles.entities.Venta
import com.example.proyectointegradorappsmoviles.repositories.VentaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class VentaViewModel(app : Application) : AndroidViewModel(app)
{
    val listaTotalVentas : LiveData<List<Venta>>
    val repositorio : VentaRepository

    init {
        val VentaDAO = HorusDatabase.getDatabase(app).ventaDao()
        repositorio = VentaRepository(VentaDAO)
        listaTotalVentas = repositorio.listadoCompletoVentas
    }

    fun insertarVenta(venta: Venta)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repositorio.insertarVenta(venta)
        }
    }

    fun borrarTodasVentas()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repositorio.borrarTodasVentas()
        }
    }

}