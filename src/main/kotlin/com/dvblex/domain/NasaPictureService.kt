package com.dvblex.domain

import com.dvblex.dto.NasaPictureInfoDto
import com.dvblex.dto.NasaResponseDto
import com.dvblex.dto.PictureInfoDto
import com.dvblex.dto.StealNasaPicturesRequestInfoDto
import com.dvblex.exception.PictureStealingException
import com.dvblex.mapper.NasaCameraInfoDtoToCameraMapper
import com.dvblex.mapper.NasaPictureInfoDtoToPictureMapper
import com.dvblex.repository.CameraRepository
import com.dvblex.repository.PictureRepository
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class NasaPictureService(
    private val cameraRepository: CameraRepository,
    private val pictureRepository: PictureRepository,
    private val nasaPictureMapper: NasaPictureInfoDtoToPictureMapper,
    private val nasaCameraMapper: NasaCameraInfoDtoToCameraMapper
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(NasaPictureService::class.java)
        private val httpClient = HttpClient()
    }


    private val camerasCache: MutableMap<Long, Camera> = HashMap()


    suspend fun stealPictures(requestInfo: StealNasaPicturesRequestInfoDto) {
        try {
            logger.info("Start processing NASA pictures for sol: ${requestInfo.sol}")

            val url = buildUrl(requestInfo)
            val nasaResponseDto = httpClient.get<NasaResponseDto>(url)

            nasaResponseDto.nasaPictureDtos.forEach { savePicture(it) }
            camerasCache.clear()

            logger.info("NASA pictures for sol '${requestInfo.sol}' have been successfully stolen!")
        } catch (e: Exception) {
            logger.error("Error saving pictures from NASA for sol: ${requestInfo.sol}", e)
            throw PictureStealingException("Error saving pictures from NASA for sol: ${requestInfo.sol}")
        }
    }

    private fun buildUrl(requestInfo: StealNasaPicturesRequestInfoDto): String {
        return "$nasaUrl?sol=${requestInfo.sol}&api_key=$nasaApiKey"
    }

    private suspend fun savePicture(nasaPictureInfoDto: NasaPictureInfoDto) {
        val nasaCameraInfoDto = nasaPictureInfoDto.camera
        val nasaCameraId = nasaCameraInfoDto.id
        var camera = camerasCache.getOrPut(nasaCameraId) {
            cameraRepository.findCameraByNasaId(nasaCameraId) as Camera
        }

        val picture = nasaPictureMapper.toPicture(nasaPictureInfoDto)
        picture.camera = camera

        saveCameraIfDoesNotExist(camera)
        pictureRepository.save(picture)
    }

    private suspend fun saveCameraIfDoesNotExist(camera: Camera) {
        if (camera.id == null) {
            cameraRepository.save(camera)
        }
    }

    suspend fun getStolenPictures(): List<PictureInfoDto> {
        return pictureRepository.getAllStolenPictures()
    }
}