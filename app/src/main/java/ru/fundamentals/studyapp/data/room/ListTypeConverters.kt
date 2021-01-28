package ru.fundamentals.studyapp.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListTypeConverters {

    val gson = Gson()

    @TypeConverter
    fun convertToString(list: List<Long>): String{
        return gson.toJson(list)
    }

    @TypeConverter
    fun convertToList(string: String): List<Long>{
        val type = object : TypeToken<List<Long?>?>() {}.type
//        val type = TypeToken<List<Long>>().type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun convertTo(list: List<String>): String{
        return list.joinToString(",")
    }

    @TypeConverter
    fun convert(string: String): List<String>{
        return string.split(",")

    }


}