package kr.hs.entrydsm.repo.domain.document.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.verify
import kr.hs.entrydsm.repo.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.repo.common.getTestDocument
import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.UpdateWriterInfoRequest
import kr.hs.entrydsm.repo.domain.major.persistence.Major
import kr.hs.entrydsm.repo.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.repo.domain.student.persistence.Student
import kr.hs.entrydsm.repo.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.repo.global.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull

internal class UpdateWriterInfoUseCaseTest : DescribeSpec({

    val documentRepository: DocumentRepository = mockk()
    val studentRepository: StudentRepository = mockk()
    val majorRepository: MajorRepository = mockk()
    mockkObject(SecurityUtil)

    val updateWriterInfoUseCase = UpdateWriterInfoUseCase(documentRepository, studentRepository, majorRepository)

    describe("updateWriterInfo") {

        val student = anyValueObject<Student>("number" to "1")
        val document = getTestDocument(student)
        val major = anyValueObject<Major>()

        val request = anyValueObject<UpdateWriterInfoRequest>()

        context("작성자 정보 데이터를 받으면") {

            val slot = slot<Document>()

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.findByWriterStudentId(student.id) } returns document
            every { majorRepository.findByIdOrNull(request.majorId) } returns major
            every { documentRepository.save(capture(slot)) } returnsArgument 0
            every { studentRepository.save(any()) } returnsArgument 0

            it("본인(학생) 문서의 작성자 정보를 수정한다.") {

                updateWriterInfoUseCase.execute(request)

                verify(exactly = 1) { documentRepository.save(slot.captured) }
                verify(exactly = 1) { studentRepository.save(any()) }
                onlyWriterIsDifferent(slot, document)
            }
        }
    }
})

private fun onlyWriterIsDifferent(
    slot: CapturingSlot<Document>,
    document: Document
) {
    slot.captured.id shouldBe document.id
    slot.captured.writer shouldNotBe document.writer
    slot.captured.introduce shouldBe document.introduce
    slot.captured.skillSet shouldBe document.skillSet
    slot.captured.projectList shouldBe document.projectList
    slot.captured.awardList shouldBe document.awardList
    slot.captured.certificateList shouldBe document.certificateList
}