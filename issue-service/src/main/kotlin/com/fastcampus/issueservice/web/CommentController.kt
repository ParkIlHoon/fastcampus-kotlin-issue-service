package com.fastcampus.issueservice.web

import com.fastcampus.issueservice.config.AuthUser
import com.fastcampus.issueservice.model.CommentRequest
import com.fastcampus.issueservice.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * 이슈 코멘트 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/issues/{issueId}/comments")
class CommentController(
    private val commentService: CommentService
) {

    /**
     * 코멘트 생성 요청
     *
     * @param authUser 사용자 인증 정보
     * @param issueId 이슈 아이디
     * @param request 코멘트 생성 정보
     */
    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable("issueId") issueId: Long,
        @RequestBody request: CommentRequest,
    ) = commentService.create(issueId, authUser.userId, authUser.username, request)

    /**
     * 코멘트 목록 조회 요청
     *
     * @param authUser 사용자 인증 정보
     * @param issueId 이슈 아이디
     */
    @GetMapping
    fun getComments(
        authUser: AuthUser,
        @PathVariable("issueId") issueId: Long,
    ) = commentService.get(issueId)

    @PutMapping("/{id}")
    fun update(
        authUser: AuthUser,
        @PathVariable("issueId") issueId: Long,
        @PathVariable("id") commentId: Long,
        @RequestBody request: CommentRequest,
    ) = commentService.update(commentId, authUser.userId, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        authUser: AuthUser,
        @PathVariable("issueId") issueId: Long,
        @PathVariable("id") commentId: Long,
    ) = commentService.delete(issueId, commentId, authUser.userId)
}