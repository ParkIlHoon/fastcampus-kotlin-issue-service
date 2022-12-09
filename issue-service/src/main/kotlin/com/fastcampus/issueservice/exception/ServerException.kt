package com.fastcampus.issueservice.exception

/**
 * 서버 예외 클래스
 *
 * @property code 커스텀 예외 코드
 * @property message 커스텀 예외 메시지
 */
sealed class ServerException(
    val code: Int,
    override val message: String,
): RuntimeException(message)

/**
 * 인증 예외 클래스
 *
 * 커스텀 예외 코드 : 401
 *
 * @property message 커스텀 예외 메시지
 */
data class UnauthorizedException(
    override val message: String = "인증 정보가 잘못되었습니다.",
): ServerException(401, message)