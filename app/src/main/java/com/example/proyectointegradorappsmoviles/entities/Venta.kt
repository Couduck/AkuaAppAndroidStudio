package com.example.proyectointegradorappsmoviles.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.sql.Date

@Parcelize
@Entity
class Venta(@PrimaryKey(autoGenerate = true) val id : Int,
            val nombreCliente : String,
            val cantidad : Int,
            val FechaHora : String,
            val pagado : Boolean,
            val total : Double): Parcelable
{
}