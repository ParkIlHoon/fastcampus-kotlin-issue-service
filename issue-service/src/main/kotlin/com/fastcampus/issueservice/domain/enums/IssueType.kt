package com.fastcampus.issueservice.domain.enums

/**
 * 이슈 타입
 */
enum class IssueType {
    // 버그
    BUG,
    // 테스크
    TASK;

    companion object {
        operator fun invoke(type: String) = valueOf(type.uppercase())
    }
}
