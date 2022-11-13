package com.fastcampus.issueservice.exception

import com.auth0.jwt.exceptions.TokenExpiredException
import mu.KotlinLogging
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 전역 예외 핸들러
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = KotlinLogging.logger {}

    /**
     * 서버 예외 핸들러
     *
     * @see ServerException
     */
    @ExceptionHandler(ServerException::class)
    fun handleServerException(ex: ServerException): ErrorResponse {
        logger.error { ex.message }
        return ErrorResponse(code = ex.code, message = ex.message)
    }

    /**
     * 토큰 만료 예외 핸들러
     */
    @ExceptionHandler(TokenExpiredException::class)
    fun handleTokenExpiredException(ex: TokenExpiredException): ErrorResponse {
        logger.error { ex.message }
        return ErrorResponse(code = 401, message = "Token Expired Error")
    }

    /**
     * 기타 예외 핸들러
     */
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ErrorResponse {
        logger.error { ex.message }
        return ErrorResponse(code = 500, message = "Error")
    }
}