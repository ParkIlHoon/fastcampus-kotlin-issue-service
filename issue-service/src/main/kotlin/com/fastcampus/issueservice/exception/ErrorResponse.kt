package com.fastcampus.issueservice.exception

/**
 * 에러 응답 클래스
 *
 * @property code 커스텀 에러 코드
 * @property message 커스텀 에러 메시지
 */
data class ErrorResponse(
    val code: Int,
    val message: String,
)