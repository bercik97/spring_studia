package com.jobbed.api.company.domain

import com.jobbed.api.company.domain.command.CreateCompanyCommand
import com.jobbed.api.shared.error.ErrorStatus
import com.jobbed.api.shared.exception.type.NotFoundException
import com.jobbed.api.shared.exception.type.ValidationException
import spock.lang.Specification

import java.util.concurrent.ConcurrentHashMap

class CompanySpec extends Specification implements CompanyFixture {

    private CompanyFacade companyFacade

    private ConcurrentHashMap<Long, CompanyEntity> db

    def setup() {
        db = new ConcurrentHashMap<>()
        companyFacade = new CompanyConfiguration().companyFacade(db)
    }

    def 'Should create company'() {
        given:
        def command = new CreateCompanyCommand('Jobbed')

        when:
        companyFacade.create(command)

        then:
        !db.isEmpty()
    }

    def 'Should throw an exception cause name already exists'() {
        given:
        def command = new CreateCompanyCommand('Jobbed')

        when:
        companyFacade.create(command)

        and:
        !db.isEmpty()

        and: 'we try to create new company with the same name as before'
        companyFacade.create(command)

        then:
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.COMPANY_NAME_TAKEN
    }

    def 'Should find company by name'() {
        given:
        def name = 'Jobbed'
        db.put(1L, createCompanyEntity(name))

        when:
        def foundCompany = companyFacade.findByName(name)

        then:
        foundCompany != null && foundCompany.name == name
    }

    def 'Should throw an exception cause cannot find company by name'() {
        given:
        def name = 'Jobbed'
        db.put(1L, createCompanyEntity(name))

        when:
        companyFacade.findByName('definitelyNotJobbed')

        then:
        NotFoundException exception = thrown()
        exception.errorStatus == ErrorStatus.COMPANY_NOT_FOUND
    }
}
