package com.dvblex.domain

import jakarta.persistence.*

@Entity
@Table(name = "cameras")
data class Camera(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cameras_id_gen")
    @SequenceGenerator(name = "cameras_id_gen", sequenceName = "cameras_id_seq", allocationSize = 1)
    val id: Long? = null,

    @Column(name = "nasa_id", nullable = false, unique = true)
    val nasaId: Long,

    @Column(name = "name", nullable = false)
    val name: String,

    @OneToMany(mappedBy = "camera", cascade = [CascadeType.ALL], orphanRemoval = true)
    val pictures: MutableList<Picture> = mutableListOf()
) {
    fun addPicture(picture: Picture) {
        picture.camera = this
        pictures.add(picture)
    }
}
