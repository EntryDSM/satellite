package com.example.exit.domain.company.persistence.repository

import com.example.exit.domain.company.persistence.StandbyCompany
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StandbyCompanyRepository : CrudRepository<StandbyCompany, UUID> {
}