package com.example.proyectointegradorappsmoviles.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.proyectointegradorappsmoviles.converters.VentaConverters
import com.example.proyectointegradorappsmoviles.daos.ClienteDao
import com.example.proyectointegradorappsmoviles.daos.VentaDao
import com.example.proyectointegradorappsmoviles.entities.Cliente
import com.example.proyectointegradorappsmoviles.entities.Venta

@Database(entities = [Cliente::class, Venta::class], version = 1)
@TypeConverters(VentaConverters::class)
abstract class HorusDatabase : RoomDatabase() {
    abstract fun ventaDao() : VentaDao
    abstract fun clienteDao() : ClienteDao

    companion object
    {
        @Volatile
        private var INSTANCE : HorusDatabase? = null

        fun getDatabase(context: Context) : HorusDatabase
        {
            val tempInstance = INSTANCE
            if(tempInstance != null)
            {
                return tempInstance
            }

            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HorusDatabase::class.java,
                    name="horus_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}