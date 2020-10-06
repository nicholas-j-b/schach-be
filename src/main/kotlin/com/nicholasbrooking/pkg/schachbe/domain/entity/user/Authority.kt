package com.nicholasbrooking.pkg.schachbe.domain.entity.user

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "AUTHORITY")
data class Authority(
        @Id
        @Column(nullable = false)
        val username: String,

        @Column(nullable = false)
        val name: String
)