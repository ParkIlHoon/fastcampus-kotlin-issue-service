package com.fastcampus.issueservice.exception

/**
 * 찾을 수 없음 예외 클래스
 *
 * 커스텀 예외 코드 : 404
 *
 * @property message 커스텀 예외 메시지
 */
sealed class NotFoundException(
    override val message: String,
): ServerException(404, message)

/**
 * 이슈를 찾을 수 없음 예외 클래스
 */
data class IssueNotFoundException(
    override val message: String = "이슈가 존재하지 않습니다."
): NotFoundException(message)