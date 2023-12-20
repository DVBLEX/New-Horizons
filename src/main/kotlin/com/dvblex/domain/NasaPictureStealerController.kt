package com.dvblex.domain

import com.dvblex.dto.StealNasaPicturesRequestInfoDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.nasaPictureStealerController(nasaPictureService: NasaPictureService) {
    route("/nasa/pictures") {
        post("/steal") {
            val requestInfo = call.receive<StealNasaPicturesRequestInfoDto>()
            nasaPictureService.stealPictures(requestInfo)
            call.respond(HttpStatusCode.Created)
        }

        get("/") {
            val pictures = nasaPictureService.getStolenPictures()
            call.respond(pictures)
        }
    }
}
