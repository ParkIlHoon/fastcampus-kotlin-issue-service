package com.fastcampus.userservice.utils

import com.fastcampus.userservice.config.JWTProperties
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class JWTUtilsTest {

    private val logger = KotlinLogging.logger {  }

    @Test
    internal fun createToken() {
        // given
        val jwtClaim = JWTClaim(userId = 1, email = "email", profileUrl = "profile", userName = "테스트")
        val jwtProperties = JWTProperties(issuer = "jara", subject = "auth", expiresTime = 3600, secret = "test-key")

        // when
        val token = JWTUtils.createToken(jwtClaim, jwtProperties)

        // then
        assertNotNull(token)
        logger.info { token }
    }

    @Test
    internal fun decodeToken() {
        // given
        val jwtClaim = JWTClaim(userId = 1, email = "email", profileUrl = "profile", userName = "테스트")
        val jwtProperties = JWTProperties(issuer = "jara", subject = "auth", expiresTime = 3600, secret = "test-key")
        val token = JWTUtils.createToken(jwtClaim, jwtProperties)

        // when
        val decodedJWT = JWTUtils.decode(token, jwtProperties.secret, jwtProperties.issuer)

        // then
        assertNotNull(decodedJWT)
        assertEquals(token, decodedJWT.token)
        assertEquals(jwtProperties.issuer, decodedJWT.issuer)
        assertEquals(jwtProperties.subject, decodedJWT.subject)
        assertNotNull(jwtProperties.issuer, decodedJWT.payload)
        with(decodedJWT) {
            assertEquals(jwtClaim.userId, claims["userId"]!!.asLong())
            assertEquals(jwtClaim.email, claims["email"]!!.asString())
            assertEquals(jwtClaim.profileUrl, claims["profileUrl"]!!.asString())
            assertEquals(jwtClaim.userName, claims["userName"]!!.asString())
        }
    }
}