package com.example.proyectointegradorappsmoviles.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyectointegradorappsmoviles.entities.Cliente
import com.example.proyectointegradorappsmoviles.entities.Venta

@Dao
interface VentaDao {
    @Insert
    fun insertarVenta(venta : Venta)

    @Query("SELECT * FROM venta")
    fun recuperarTodasVentas() : LiveData<List<Venta>>

    @Query("DELETE FROM venta")
    fun borrarTodasVentas()
}