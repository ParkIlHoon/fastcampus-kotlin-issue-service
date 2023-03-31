package com.fastcampus.userservice.controller

import com.fastcampus.userservice.domain.service.UserService
import com.fastcampus.userservice.model.SignUpRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {

    @PostMapping
    suspend fun signUp(@RequestBody request: SignUpRequest) = userService.signUp(request)

}