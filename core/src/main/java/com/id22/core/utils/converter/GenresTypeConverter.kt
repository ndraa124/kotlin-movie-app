package com.id22.core.utils.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.id22.core.data.source.local.entity.GenresEntity

class GenresTypeConverter {
    @TypeConverter
    fun saveGenresList(list: List<GenresEntity>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun getGenresList(list: String): List<GenresEntity> {
        return Gson().fromJson(list, object : TypeToken<List<GenresEntity>>() {}.type)
    }
}
