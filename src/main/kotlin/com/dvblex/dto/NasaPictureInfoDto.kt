package com.dvblex.dto

import java.time.LocalDate

data class NasaPictureInfoDto(
    val id: Long,
    val imgSrc: String,
    val createdAt: LocalDate,
    val camera: NasaCameraInfoDto
)
