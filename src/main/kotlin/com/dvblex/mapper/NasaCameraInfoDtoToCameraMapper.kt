package com.dvblex.mapper

import com.dvblex.domain.Camera
import com.dvblex.dto.NasaCameraInfoDto

class NasaCameraInfoDtoToCameraMapper {
    fun toCamera(nasaCameraInfoDto: NasaCameraInfoDto): Camera {
        return Camera(nasaId = nasaCameraInfoDto.id, name = nasaCameraInfoDto.name)
    }
}