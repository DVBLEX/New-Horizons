package com.dvblex.repository

import com.dvblex.domain.Picture
import com.dvblex.dto.PictureInfoDto


interface PictureRepository {
    suspend fun getAllStolenPictures(): List<PictureInfoDto>
    suspend fun save(picture: Picture): Picture
}