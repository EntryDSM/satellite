package kr.hs.entrydsm.repo.domain.library.presentation.dto

data class StudentQueryLibraryResponse(
    val libraryDocumentList: List<LibraryDocumentElement>
) {
    data class LibraryDocumentElement(
        val year: Int,
        val grade: Int,
        val generation: Int,
        val documentUrl: String
    )
}