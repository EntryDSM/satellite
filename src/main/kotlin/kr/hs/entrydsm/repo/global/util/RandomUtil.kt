package kr.hs.entrydsm.repo.global.util

import java.util.Random
import java.util.UUID
import kotlin.math.pow
import kr.hs.entrydsm.repo.global.exception.InternalServerException

object RandomUtil {

    private val RANDOM = Random()

    fun randomNumeric(length: Int): String {
        if (length == 0) return ""
        else if (length > 19) throw InternalServerException
        val bound = (10.0).pow(length).toLong()
        return String.format("%0${length}d", RANDOM.nextLong(bound))
    }

    fun random(length: Int): String {
        if (length == 0) return ""
        else if (length > 32) throw InternalServerException
        return UUID.randomUUID()
            .toString()
            .replace("-", "")
            .substring(length)
    }
}