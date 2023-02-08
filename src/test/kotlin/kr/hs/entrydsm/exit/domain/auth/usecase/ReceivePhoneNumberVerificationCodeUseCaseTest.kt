package kr.hs.entrydsm.exit.domain.auth.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kr.hs.entrydsm.exit.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.exit.domain.auth.exception.VerificationCodeMismatchedException
import kr.hs.entrydsm.exit.domain.auth.persistence.VerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.VerificationCodeRepository
import kr.hs.entrydsm.exit.domain.auth.properties.VerificationCodeProperties
import org.springframework.data.repository.findByIdOrNull

internal class ReceivePhoneNumberVerificationCodeUseCaseTest : DescribeSpec({

    val verificationCodeRepository: VerificationCodeRepository = mockk()
    val properties: VerificationCodeProperties = anyValueObject()

    val verifyCodeUseCase = VerifyCodeUseCase(verificationCodeRepository, properties)

    describe("receivePhoneNumberVerificationCode"){

        val phoneNumber = "01012345678"
        val code = "123456"
        val verificationCode = anyValueObject<VerificationCode>(
            "code" to code
        )

        context("전화번호와 코드가 주어지면") {

            every { verificationCodeRepository.findByIdOrNull(phoneNumber) } returns verificationCode
            every { verificationCodeRepository.save(any()) } returnsArgument 0

            it("저장된 코드를 인증상태로 변경한다.") {

                verifyCodeUseCase.execute(phoneNumber, code)

                verify(exactly = 1) { verificationCodeRepository.save(any()) }
            }
        }

        val wrongCode = "234567"

        context("잘못된 코드가 주어지면") {

            every { verificationCodeRepository.findByIdOrNull(phoneNumber) } returns verificationCode
            every { verificationCodeRepository.save(any()) } returnsArgument 0

            it("VerificationCodeMismatched 예외를 던진다.") {
                shouldThrow<VerificationCodeMismatchedException> {
                    verifyCodeUseCase.execute(phoneNumber, wrongCode)
                }
                verify(exactly = 0) { verificationCodeRepository.save(any()) }
            }
        }
    }
})
