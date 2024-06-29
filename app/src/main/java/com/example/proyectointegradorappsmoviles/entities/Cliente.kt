package com.example.proyectointegradorappsmoviles.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class Cliente(@PrimaryKey val nombreCorto : String,
              val nombreCompleto : String,
              val precioVenta : Double,
              val capaCliente : String): Parcelable
{

}