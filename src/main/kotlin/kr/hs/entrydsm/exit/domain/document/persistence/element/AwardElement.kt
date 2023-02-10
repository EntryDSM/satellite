package kr.hs.entrydsm.exit.domain.document.persistence.element

import java.util.*
import javax.persistence.Transient

class AwardElement(

    elementId: UUID? = null,
    val name: String,
    val awardingInstitution: String,
    val date: Date,
    val description: String?,
    val url: String?

) : AbstractElement(elementId) {
    @get:Transient
    override val elementName: String
        get() = "수상경력 $name"
}