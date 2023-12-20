package com.dvblex.domain

import java.time.LocalDate
import jakarta.persistence.*

@Entity
@Table(name = "pictures")
data class Picture(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pictures_id_gen")
    @SequenceGenerator(name = "pictures_id_gen", sequenceName = "pictures_id_seq", allocationSize = 1)
    val id: Long? = null,

    @Column(name = "nasa_id", nullable = false, unique = true)
    val nasaId: Long,

    @Column(name = "img_src", nullable = false)
    val imgSrc: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDate
)

