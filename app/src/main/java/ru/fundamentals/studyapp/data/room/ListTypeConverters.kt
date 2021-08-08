package ru.fundamentals.studyapp.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.fundamentals.studyapp.data.network.models.ImagesDto

class ListTypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun convertListLongToString(list: List<Long>): String{
        return gson.toJson(list)
    }

    @TypeConverter
    fun convertStringToListLong(string: String): List<Long>{
        val type = object : TypeToken<List<Long?>?>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun convertListStringToString(list: List<String>): String{
        return list.joinToString(",")
    }

    @TypeConverter
    fun convertStringToListString(string: String): List<String>{
        return string.split(",")

    }

    @TypeConverter
    fun convertImagesDtoToString(images: ImagesDto): String{
        return gson.toJson(images)
    }
    @TypeConverter
    fun convertStringToImagesDto(string: String): ImagesDto{
        val type = object : TypeToken<ImagesDto?>() {}.type
        return gson.fromJson(string, type)
    }

}