package com.fastcampus.issueservice.web

import com.fastcampus.issueservice.config.AuthUser
import com.fastcampus.issueservice.domain.enums.IssueStatus
import com.fastcampus.issueservice.model.IssueRequest
import com.fastcampus.issueservice.service.IssueService
import org.springframework.http.HttpStatus
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

    /**
     * 이슈 단건 조회 요청
     *
     * @param authUser 사용자 인증 정보
     * @param id 조회할 이슈 아이디
     */
    @GetMapping("/{id}")
    fun getDetail(
        authUser: AuthUser,
        @PathVariable("id") id: Long
    ) = issueService.get(id)

    /**
     * 이슈 수정 요청
     * @param authUser 사용자 인증 정보
     * @param id 수정할 이슈 아이디
     * @param request 수정할 정보
     */
    @PutMapping("/{id}")
    fun update(
        authUser: AuthUser,
        @PathVariable("id") id: Long,
        @RequestBody request: IssueRequest
    ) = issueService.update(id = id, userId = authUser.userId, request)

    /**
     * 이슈 삭제 요청
     * @param authUser 사용자 인증 정보
     * @param id 삭제할 이슈 아이디
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        authUser: AuthUser,
        @PathVariable("id") id: Long
    ) = issueService.delete(id)

}