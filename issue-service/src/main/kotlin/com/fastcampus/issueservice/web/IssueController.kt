package com.fastcampus.issueservice.web

import com.fastcampus.issueservice.config.AuthUser
import com.fastcampus.issueservice.domain.enums.IssueStatus
import com.fastcampus.issueservice.model.IssueRequest
import com.fastcampus.issueservice.service.IssueService
import org.springframework.web.bind.annotation.*

/**
 * 이슈 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueService
) {

    /**
     * 이슈 생성 요청
     *
     * @param authUser 사용자 인증 정보
     * @param request 이슈 생성 정보
     */
    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: IssueRequest
    ) = issueService.create(userId = authUser.userId, request)

    /**
     * 이슈 목록 조회 요청
     *
     * @param authUser 사용자 인증 정보
     * @param status 이슈 상태
     */
    @GetMapping
    fun getAll(
        authUser: AuthUser,
        @RequestParam(required = false, defaultValue = "TODO") status: IssueStatus,
    ) = issueService.getAll(userId = authUser.userId, status)
}