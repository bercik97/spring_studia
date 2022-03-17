package com.jobbed.api.company.domain

trait CompanyFixture {

    static CompanyEntity createCompanyEntity(def name = 'Jobbed') {
        return new CompanyEntity(name)
    }
}
