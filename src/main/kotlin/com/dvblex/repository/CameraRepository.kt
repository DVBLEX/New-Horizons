package com.dvblex.repository

import com.dvblex.domain.Camera
import java.util.*

interface CameraRepository {
    suspend fun findCameraByNasaId(nasaId: Long): Optional<Camera>
    suspend fun save(camera: Camera): Camera
}