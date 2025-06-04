package ru.thegod.security.user.controllers

import io.micronaut.http.annotation.Controller
import ru.thegod.security.registration.services.RegistrationService
import ru.thegod.security.user.services.UserService

@Controller
class UserController(private val userService: UserService,
                     private val registrationService: RegistrationService
) {


}