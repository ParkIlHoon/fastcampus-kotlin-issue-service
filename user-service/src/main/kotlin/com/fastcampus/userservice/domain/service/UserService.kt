package com.fastcampus.userservice.domain.service

import com.fastcampus.userservice.config.JWTProperties
import com.fastcampus.userservice.domain.entity.User
import com.fastcampus.userservice.domain.repository.UserRepository
import com.fastcampus.userservice.exception.InvalidJwtTokenException
import com.fastcampus.userservice.exception.PasswordNotMatchedException
import com.fastcampus.userservice.exception.UserExistsException
import com.fastcampus.userservice.exception.UserNotFoundException
import com.fastcampus.userservice.model.MeResponse
import com.fastcampus.userservice.model.SignInRequest
import com.fastcampus.userservice.model.SignInResponse
import com.fastcampus.userservice.model.SignUpRequest
import com.fastcampus.userservice.utils.BCryptUtils
import com.fastcampus.userservice.utils.JWTClaim
import com.fastcampus.userservice.utils.JWTUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
    private val cacheManager: CoroutineCacheManager<User>,
) {

    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

    suspend fun signUp(signUpRequest: SignUpRequest) {
        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistsException()
            }

            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username
            )

            userRepository.save(user)
        }
    }

    suspend fun signIn(request: SignInRequest): SignInResponse {
        return with(userRepository.findByEmail(request.email) ?: throw UserNotFoundException()) {
            val verified = BCryptUtils.verify(request.password, password)
            if (!verified) {
                throw PasswordNotMatchedException()
            }

            val jwtClaim = JWTClaim(userId = id!!, email = email, profileUrl = profileUrl, userName = username)
            val token = JWTUtils.createToken(jwtClaim, jwtProperties)

            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)

            SignInResponse(
                email = email,
                username = username,
                token = token
            )
        }
    }

    suspend fun logout(token: String) {
        cacheManager.awaitEvict(token)
    }

    suspend fun getByToken(token: String): MeResponse {
        val user = getUser(token)

        return MeResponse(
            id = user.id!!,
            profileUrl = user.profileUrl,
            username = user.username,
            email = user.email,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
        )
    }

    suspend fun get(userId: Long) : User = userRepository.findById(userId) ?: throw UserNotFoundException()

    suspend fun edit(token: String, username: String, profileUrl: String?): User {
        val user = getUser(token)
        val updatedUser = user.copy(username = username, profileUrl = profileUrl ?: user.profileUrl)
        return userRepository.save(updatedUser)
            .also { cacheManager.awaitPut(key = token, value = it, ttl = CACHE_TTL) }
    }

    private suspend fun getUser(token: String): User {
        return cacheManager.awaitGetOrPut(key = token, ttl = CACHE_TTL) {
            // 캐시가 유효하지 않을 때 동작
            val decodedJWT = JWTUtils.decode(token, jwtProperties.secret, jwtProperties.issuer)
            val userId: Long = decodedJWT.claims["userId"]?.asLong() ?: throw InvalidJwtTokenException()

            get(userId)
        }
    }
}