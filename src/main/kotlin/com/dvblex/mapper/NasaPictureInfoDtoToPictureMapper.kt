package com.dvblex.mapper

import com.dvblex.domain.Picture
import com.dvblex.dto.NasaPictureInfoDto

class NasaPictureInfoDtoToPictureMapper {
    fun toPicture(nasaPictureInfoDto: NasaPictureInfoDto): Picture {
        return Picture(
            nasaId = nasaPictureInfoDto.id,
            imgSrc = nasaPictureInfoDto.imgSrc,
            createdAt = nasaPictureInfoDto.createdAt
        )
    }
}