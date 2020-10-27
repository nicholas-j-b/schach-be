package com.nicholasbrooking.pkg.schachbe.service.exception.auth

import com.nicholasbrooking.pkg.schachbe.service.exception.SchachbeException

class SchachbeUserDisallowed(message: String = "User not allowed to perform action"): SchachbeException(message)