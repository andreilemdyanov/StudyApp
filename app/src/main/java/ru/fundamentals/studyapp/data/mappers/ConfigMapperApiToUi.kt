package ru.fundamentals.studyapp.data.mappers

import ru.fundamentals.studyapp.data.models.Config
import ru.fundamentals.studyapp.data.network.models.ConfigResponse

object ConfigMapperApiToUi {

    fun transform(config: ConfigResponse): Config {
        return Config(
            config.imagesDto.secureBaseUrl,
            config.imagesDto.posterSizes[2],
            config.imagesDto.backdropSizes[2],
            config.imagesDto.profileSizes[1]
        )
    }
}