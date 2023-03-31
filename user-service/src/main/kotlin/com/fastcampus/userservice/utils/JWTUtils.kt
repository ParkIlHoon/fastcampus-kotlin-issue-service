package com.fastcampus.userservice.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fastcampus.userservice.config.JWTProperties
import java.util.*

object JWTUtils {
    fun createToken(claim: JWTClaim, properties: JWTProperties) =
        JWT.create()
            .withIssuer(properties.issuer)
            .withSubject(properties.subject)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + properties.expiresTime * 1000))
            .withClaim("userId", claim.userId)
            .withClaim("email", claim.email)
            .withClaim("profileUrl", claim.profileUrl)
            .withClaim("userName", claim.userName)
            .sign(Algorithm.HMAC256(properties.secret))


    fun decode(token: String, secret: String, issuer: String) =
        JWT.require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .build()
            .verify(token)

}

data class JWTClaim(
    val userId: Long,
    val email: String,
    val profileUrl: String,
    val userName: String,
)