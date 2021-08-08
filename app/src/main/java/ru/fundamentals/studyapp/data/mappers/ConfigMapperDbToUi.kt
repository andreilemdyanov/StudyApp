package ru.fundamentals.studyapp.data.mappers

import ru.fundamentals.studyapp.data.models.Config
import ru.fundamentals.studyapp.data.room.models.ConfigDb

object ConfigMapperDbToUi {

    fun transform(configDb: ConfigDb): Config {
        return Config(
            configDb.secureBaseUrl,
            configDb.posterSize,
            configDb.backdropSize,
            configDb.profileSize
        )
    }
}