package ru.fundamentals.studyapp.data.mappers

import ru.fundamentals.studyapp.data.models.Config
import ru.fundamentals.studyapp.data.room.models.ConfigDb

object ConfigMapperUiToDb {

    fun transform(config: Config): ConfigDb {
        return ConfigDb(
            1,
            config.secureBaseUrl,
            config.posterSize,
            config.backdropSize,
            config.profileSize
        )
    }
}