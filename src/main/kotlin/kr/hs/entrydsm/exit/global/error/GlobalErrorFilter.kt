package kr.hs.entrydsm.exit.global.error

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hs.entrydsm.exit.global.error.custom.CustomErrorProperty
import kr.hs.entrydsm.exit.global.error.custom.CustomException
import kr.hs.entrydsm.exit.global.error.response.DefaultErrorResponse
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GlobalErrorFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            when (e) {
                is CustomException -> {
                    sendErrorMessage(response, e.errorProperty)
                }
                else -> {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun sendErrorMessage(response: HttpServletResponse, errorProperty: CustomErrorProperty) {

        response.status = errorProperty.status()
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        response.writer.write(
            objectMapper.writeValueAsString(
                DefaultErrorResponse(
                    status = errorProperty.status(),
                    message = errorProperty.message(),
                    code = errorProperty.code()
                )
            )
        )
    }
}