package com.dvblex.dto

import java.time.LocalDate

data class PictureInfoDto(
    val id: Long,
    val nasaId: Long,
    val imgSrc: String,
    val createdAt: LocalDate,
    val cameraInfoDto: CameraInfoDto
)
