package com.example.proyectointegradorappsmoviles.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectointegradorappsmoviles.entities.Cliente

@Dao
interface ClienteDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarCliente(cliente : Cliente)

    @Query("SELECT * FROM cliente")
    fun recuperarTodosClientes() : LiveData<List<Cliente>>

    @Query("SELECT * FROM cliente WHERE nombreCorto = :nombreCorto")
    fun recuperarCliente(nombreCorto : String) : Cliente

    @Query("SELECT nombreCorto FROM cliente")
    fun recuperarNombresClientes() : LiveData<List<String>>

    @Query("DELETE FROM cliente")
    fun borrarTodosClientes()
}