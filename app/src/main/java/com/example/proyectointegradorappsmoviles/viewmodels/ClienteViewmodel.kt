package com.example.proyectointegradorappsmoviles.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.proyectointegradorappsmoviles.databases.HorusDatabase
import com.example.proyectointegradorappsmoviles.entities.Cliente
import com.example.proyectointegradorappsmoviles.repositories.ClienteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClienteViewmodel (app : Application) : AndroidViewModel(app)
{
    val listaTotalClientes : LiveData<List<Cliente>>
    val listaNombresClientes : LiveData<List<String>>
    val repositorio : ClienteRepository

    init {
        val ClienteDAO = HorusDatabase.getDatabase(app).clienteDao()
        repositorio = ClienteRepository(ClienteDAO)
        listaTotalClientes = repositorio.listadoCompletoClientes
        listaNombresClientes = repositorio.listadoNombresCliente
    }

    fun insertarCliente(cliente : Cliente)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repositorio.insertarCliente(cliente)
        }
    }

    fun recuperarCliente(nombreCorto : String)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repositorio.recuperarCliente(nombreCorto)
        }
    }

    /*fun recuperarNombresCliente()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repositorio.recuperarNombresClientes()
        }
    }*/

    fun borrarTodosClientes()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repositorio.borrarTodosClientes()
        }
    }
}