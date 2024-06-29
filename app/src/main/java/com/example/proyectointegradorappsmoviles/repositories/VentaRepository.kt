package com.example.proyectointegradorappsmoviles.repositories

import androidx.lifecycle.LiveData
import com.example.proyectointegradorappsmoviles.daos.VentaDao
import com.example.proyectointegradorappsmoviles.entities.Venta

class VentaRepository(private val VentaDAO : VentaDao){
    val listadoCompletoVentas: LiveData<List<Venta>> = VentaDAO.recuperarTodasVentas()

    fun insertarVenta(venta : Venta)
    {
        VentaDAO.insertarVenta(venta)
    }

    fun borrarTodasVentas()
    {
        VentaDAO.borrarTodasVentas()
    }
}