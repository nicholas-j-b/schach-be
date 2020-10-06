package com.nicholasbrooking.pkg.schachbe.service.user

import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserRole
import com.nicholasbrooking.pkg.schachbe.domain.repository.AuthorityRepository
import org.springframework.stereotype.Service

@Service
class AuthorityService(
        private val authorityRepository: AuthorityRepository
) {

}


