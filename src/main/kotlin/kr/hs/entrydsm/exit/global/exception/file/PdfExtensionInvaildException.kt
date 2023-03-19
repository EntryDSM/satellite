package kr.hs.entrydsm.exit.global.exception.file

import kr.hs.entrydsm.exit.global.error.GlobalErrorCode
import kr.hs.entrydsm.exit.global.error.custom.GlobalCustomException


object PdfExtensionInvaildException : GlobalCustomException(
    GlobalErrorCode.PDF_EXTENSION_INVALID
)