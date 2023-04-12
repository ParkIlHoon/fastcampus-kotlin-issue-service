package com.fastcampus.issueservice.config

import com.fastcampus.issueservice.exception.UnauthorizedException
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

/**
 * MVC 설정
 */
@Configuration
class WebConfig(
    private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver,
): WebMvcConfigurationSupport() {

    /**
     * 파라미터 Resolver 추가
     *
     * 1. 사용자 인증 정보 주입 Resolver
     */
    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.apply {
            add(authUserHandlerArgumentResolver)
        }
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations(
                "classpath:/META-INF/resources/",
                "classpath:/resources/",
                "classpath:/static/",
                "classpath:/public/",
            )
    }
}

/**
 * 사용자 인증 정보 주입 Resolver
 *
 * 메서드 파라미터 타입이 [AuthUser] 와 일치할 경우 사용자 인증정보를 주입합니다.
 *
 * @see AuthUser 주입되는 사용자 인증정보 클래스
 */
@Component
class AuthUserHandlerArgumentResolver(
    @Value("\${auth.url}") val authUrl: String,
) :HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        AuthUser::class.java.isAssignableFrom(parameter.parameterType)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authHeader: String = webRequest.getHeader("Authorization") ?: throw UnauthorizedException()

        return runBlocking {
            WebClient.create()
                .get()
                .uri(authUrl)
                .header("Authorization", authHeader)
                .retrieve()
                .awaitBody<AuthUser>()
        }
    }
}

/**
 * 사용자 인증정보
 *
 * @property userId 사용자 아이디
 * @property username 사용자 이름
 * @property profileUrl 프로필 주소
 */
data class AuthUser(
    @JsonProperty("id")
    val userId: Long,
    val username: String,
    val email: String,
    val profileUrl: String? = null
)