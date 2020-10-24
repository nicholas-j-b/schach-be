package com.nicholasbrooking.pkg.schachbe.domain.entity.board

import com.nicholasbrooking.pkg.schachbe.domain.model.Colour
import org.hibernate.annotations.Type
import java.sql.Blob
import javax.persistence.*

@Entity
class BoardState(
        @Id
        @GeneratedValue
        @Column(nullable = false)
        val id: Long = 0,

        @Lob
        @Type(type = "org.hibernate.type.BlobType")
        @Column(nullable = false, columnDefinition = "MEDIUMBLOB")
        val whiteStatusDto: Blob,

        @Lob
        @Type(type = "org.hibernate.type.BlobType")
        @Column(nullable = false, name = "black_status_dto", columnDefinition = "MEDIUMBLOB")
        val blackStatusDto: Blob,

        @Column(nullable = false)
        val turn: Colour,

        @Lob
        @Type(type = "org.hibernate.type.BlobType")
        @Column(nullable = false, columnDefinition = "MEDIUMBLOB")
        val history: Blob
)