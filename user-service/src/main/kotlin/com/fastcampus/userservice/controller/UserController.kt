package com.fastcampus.userservice.controller

import com.fastcampus.userservice.domain.service.UserService
import com.fastcampus.userservice.model.*
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/signup")
    suspend fun signUp(@RequestBody request: SignUpRequest) = userService.signUp(request)

    @PostMapping("/signin")
    suspend fun signIn(@RequestBody request: SignInRequest) : SignInResponse = userService.signIn(request)

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun logout(@AuthToken token: String) = userService.logout(token)

    @GetMapping("/me")
    suspend fun get(@AuthToken token: String) = userService.getByToken(token)

    @PostMapping("/me", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    suspend fun update(@AuthToken token: String,
                       @ModelAttribute request: UserEditRequest,
                       @RequestPart("profileUrl") filePart: FilePart,) {
        val orgFilename = filePart.filename()
        var filename: String? = null

        val user = userService.getByToken(token)

        if (orgFilename.isNotBlank()) {
            val ext = orgFilename.substring(orgFilename.lastIndexOf(".") + 1)
            filename = "${user.id}.${ext}"

            val file = File(ClassPathResource("/images/").file, filename)
            filePart.transferTo(file).awaitSingleOrNull()
        }

        userService.edit(token, request.username, filename)
    }

    @GetMapping("/{userId}/username")
    suspend fun getUserName(@PathVariable userId: Long): Map<String, String> =
        mapOf("reporter" to userService.get(userId).username)

}