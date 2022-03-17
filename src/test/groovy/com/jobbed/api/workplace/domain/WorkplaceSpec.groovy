package com.jobbed.api.workplace.domain

import com.jobbed.api.shared.error.ErrorStatus
import com.jobbed.api.shared.exception.type.NotFoundException
import com.jobbed.api.shared.exception.type.ValidationException
import com.jobbed.api.workplace.domain.command.CreateWorkplaceCommand
import com.jobbed.api.workplace.domain.command.DeleteWorkplaceCommand
import com.jobbed.api.workplace.domain.command.UpdateWorkplaceCommand
import com.jobbed.api.workplace.domain.dto.CreateWorkplaceDto
import com.jobbed.api.workplace.domain.vo.WorkplaceLocation
import org.springframework.data.domain.Pageable
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.ConcurrentHashMap

class WorkplaceSpec extends Specification implements WorkplaceFixture {

    private WorkplaceFacade workplaceFacade

    private ConcurrentHashMap<Long, WorkplaceEntity> db

    def setup() {
        db = new ConcurrentHashMap<>()
        workplaceFacade = new WorkplaceConfiguration().workplaceFacade(db)
    }

    def 'Should create workplace'() {
        given:
        def dto = createWorkplaceDto()
        def companyName = 'Jobbed'
        def command = CreateWorkplaceCommand.of(dto, companyName)

        when:
        workplaceFacade.create(command)

        then:
        db.size() == 1
    }

    @Unroll
    def 'Should throw an exception cause some of fields are invalid'(expectedErrorStatus,
                                                                     name,
                                                                     address,
                                                                     description,
                                                                     location) {
        given:
        def dto = new CreateWorkplaceDto(
                name,
                address,
                description,
                location)
        def companyName = 'Jobbed'

        when: 'we try to create an user'
        workplaceFacade.create(CreateWorkplaceCommand.of(dto, companyName))

        then: 'exception is thrown'
        ValidationException exception = thrown()
        exception.errorStatus == expectedErrorStatus

        where:
        expectedErrorStatus                    | name     | address              | description           | location                          | _
        ErrorStatus.WORKPLACE_NAME_NULL        | null     | 'Diamond street 1/4' | 'Every monday at 6pm' | new WorkplaceLocation(10.1, 10.1) | _
        ErrorStatus.WORKPLACE_ADDRESS_NULL     | 'Office' | null                 | 'Every monday at 6pm' | new WorkplaceLocation(10.1, 10.1) | _
        ErrorStatus.WORKPLACE_DESCRIPTION_NULL | 'Office' | 'Diamond street 1/4' | null                  | new WorkplaceLocation(10.1, 10.1) | _
        ErrorStatus.WORKPLACE_LOCATION_NULL    | 'Office' | 'Diamond street 1/4' | 'Every monday at 6pm' | null                              | _
    }

    def 'Should throw an exception cause name already exists'() {
        given:
        def name = 'Office'
        def dto = createWorkplaceDto(name)
        def companyName = 'Jobbed'
        def command = CreateWorkplaceCommand.of(dto, companyName)

        when:
        workplaceFacade.create(command)

        and:
        !db.isEmpty()

        and: 'we try to create new workplace with the same name as before'
        workplaceFacade.create(command)

        then:
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.WORKPLACE_NAME_TAKEN
    }

    def 'Should throw an exception cause latitude is equal to zero'() {
        given:
        def latitude = 0.0
        def location = new WorkplaceLocation(latitude, 10.1)
        def dto = createWorkplaceDto('Office', 'Diamond street 1/4', 'Every monday at 6pm', location)
        def command = CreateWorkplaceCommand.of(dto, 'Jobbed')

        when:
        workplaceFacade.create(command)

        then:
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.WORKPLACE_LATITUDE_CANNOT_BE_ZERO
    }

    def 'Should throw an exception cause longitude is equal to zero'() {
        given:
        def longitude = 0.0
        def location = new WorkplaceLocation(10.1, longitude)
        def dto = createWorkplaceDto('Office', 'Diamond street 1/4', 'Every monday at 6pm', location)
        def command = CreateWorkplaceCommand.of(dto, 'Jobbed')

        when:
        workplaceFacade.create(command)

        then:
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.WORKPLACE_LONGITUDE_CANNOT_BE_ZERO
    }

    def 'Should update workplace'() {
        given:
        def id = 1L
        def companyName = 'Jobbed'
        def workplace = createWorkplaceEntity(id, companyName)
        db.put(id, workplace)

        and:
        def newName = 'newName'
        def newDescription = 'newDescription'
        def dto = createUpdateWorkplaceDto(newName, newDescription)

        when: 'we try to update name and description'
        workplaceFacade.updateByIdAndCompanyName(UpdateWorkplaceCommand.of(id, dto, companyName))

        then: 'values are updated'
        def updatedWorkplace = workplaceFacade.findByIdAndCompanyName(workplace.id, workplace.companyName)
        updatedWorkplace.name() == newName
        updatedWorkplace.description() == newDescription
    }

    def 'Should throw an exception while trying to update workplace by non existing id or company name'() {
        when:
        workplaceFacade.deleteByIdAndCompanyName(new DeleteWorkplaceCommand(999L, 'nonExistingCompanyName'))

        then:
        NotFoundException exception = thrown()
        exception.errorStatus == ErrorStatus.WORKPLACE_NOT_FOUND
    }

    def 'Should find all workplaces by company name'() {
        given:
        def companyName = 'Jobbed'
        db.put(1L, createWorkplaceEntity(1L, companyName))
        db.put(2L, createWorkplaceEntity(2L, companyName))

        when:
        def foundWorkplaces = workplaceFacade.findAllByCompanyName(Pageable.unpaged(), companyName)

        then:
        !foundWorkplaces.isEmpty() && foundWorkplaces.size == db.size()
    }

    def 'Should find workplace by id and company name'() {
        given:
        def id = 1L
        def companyName = 'Jobbed'
        db.put(id, createWorkplaceEntity(id, companyName))

        when:
        def foundWorkplace = workplaceFacade.findByIdAndCompanyName(id, companyName)

        then:
        foundWorkplace != null
    }

    def 'Should throw an exception while trying to find workplace by non existing id or company name'() {
        when: 'we try to find workplace by non existing id and non existing company name'
        workplaceFacade.findByIdAndCompanyName(999L, 'nonExistingCompanyName')

        then: 'exception is thrown'
        NotFoundException exception = thrown()
        exception.errorStatus == ErrorStatus.WORKPLACE_NOT_FOUND
    }

    def 'Should delete workplace by id and company name'() {
        given:
        def id = 1L
        def companyName = 'Jobbed'
        db.put(id, createWorkplaceEntity(id, companyName))

        when:
        workplaceFacade.deleteByIdAndCompanyName(new DeleteWorkplaceCommand(id, companyName))

        then:
        db.isEmpty()
    }

    def 'Should throw an exception while trying to delete workplace by non existing id or company name'() {
        when: 'we try to delete workplace by non existing id and non existing company name'
        workplaceFacade.deleteByIdAndCompanyName(new DeleteWorkplaceCommand(999L, 'nonExistingCompanyName'))

        then: 'exception is thrown'
        NotFoundException exception = thrown()
        exception.errorStatus == ErrorStatus.WORKPLACE_NOT_FOUND
    }
}
