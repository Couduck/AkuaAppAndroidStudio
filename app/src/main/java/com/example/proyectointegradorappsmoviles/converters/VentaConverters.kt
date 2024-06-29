package com.example.proyectointegradorappsmoviles.converters

import androidx.room.TypeConverter
import java.sql.Date

class VentaConverters
{
    @TypeConverter
    fun timestampLong_fecha(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun fecha_timestampLong(date: Date?): Long? {
        return date?.time?.toLong()
    }
}