package com.example.proyectointegradorappsmoviles.repositories

import androidx.lifecycle.LiveData
import com.example.proyectointegradorappsmoviles.daos.ClienteDao
import com.example.proyectointegradorappsmoviles.entities.Cliente

class ClienteRepository (private val ClienteDAO : ClienteDao){
    val listadoCompletoClientes: LiveData<List<Cliente>> = ClienteDAO.recuperarTodosClientes()
    val listadoNombresCliente : LiveData<List<String>> = ClienteDAO.recuperarNombresClientes()

    fun insertarCliente(cliente : Cliente)
    {
        ClienteDAO.insertarCliente(cliente)
    }

    fun recuperarCliente(nombreCortoCliente : String)
    {
        ClienteDAO.recuperarCliente(nombreCortoCliente)
    }

    /*fun recuperarNombresClientes() : <LiveData<List<String>>
    {
        return ClienteDAO.recuperarNombresClientes()
    }*/

    fun borrarTodosClientes()
    {
        ClienteDAO.borrarTodosClientes()
    }
}