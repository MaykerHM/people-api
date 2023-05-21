package com.elotech.people.domain.person.repository;

import com.elotech.people.domain.person.Person;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PersonSpecificationTest {

    String NAME = "Fake Name";

    String DOCUMENT = "76562599024";

    LocalDate BIRTHDATE_START = LocalDate.parse("1990-01-01");

    LocalDate BIRTHDATE_END = LocalDate.parse("2010-12-31");

    private CriteriaBuilder criteriaBuilderMock;

    private CriteriaQuery criteriaQueryMock;

    private Root<Person> personRootMock;

    @BeforeEach
    public void setUp() {
        criteriaBuilderMock = mock(CriteriaBuilder.class);
        criteriaQueryMock = mock(CriteriaQuery.class);
        personRootMock = mock(Root.class);
    }

    @Test
    public void findByCriteria_whenHasNameOnly_shouldReturnPredicate() {

        PersonSearchCriteria criteria = new PersonSearchCriteria(NAME, null, null, null);

        Path namePathMock = mock(Path.class);
        when(personRootMock.get("name")).thenReturn(namePathMock);

        Expression nameToLowerExpressionMock = mock(Expression.class);
        when(criteriaBuilderMock.lower(namePathMock)).thenReturn(nameToLowerExpressionMock);

        Predicate nameIsLikePredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.like(nameToLowerExpressionMock, '%' + criteria.getName().toLowerCase() + '%')).thenReturn(nameIsLikePredicateMock);

        Predicate returnedPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.and(nameIsLikePredicateMock)).thenReturn(returnedPredicateMock);

        Specification<Person> specification = PersonSpecification.findByCriteria(criteria);
        Predicate predicate = specification.toPredicate(personRootMock, criteriaQueryMock, criteriaBuilderMock);

        assertEquals(returnedPredicateMock, predicate);
    }

    @Test
    public void findByCriteria_whenHasDocumentOnly_shouldReturnPredicate() {

        PersonSearchCriteria criteria = new PersonSearchCriteria(null, DOCUMENT, null, null);

        Path documentPathMock = mock(Path.class);
        when(personRootMock.get("document")).thenReturn(documentPathMock);

        Predicate documentPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.equal(documentPathMock, criteria.getDocument())).thenReturn(documentPredicateMock);

        Predicate returnedPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.and(documentPredicateMock)).thenReturn(returnedPredicateMock);

        Specification<Person> specification = PersonSpecification.findByCriteria(criteria);
        Predicate predicate = specification.toPredicate(personRootMock, criteriaQueryMock, criteriaBuilderMock);

        assertEquals(returnedPredicateMock, predicate);
    }

    @Test
    public void findByCriteria_whenHasBirthDateStartOnly_shouldReturnPredicate() {

        PersonSearchCriteria criteria = new PersonSearchCriteria(null, null, BIRTHDATE_START, null);

        Path birthdateStartPathMock = mock(Path.class);
        when(personRootMock.get("birthdate")).thenReturn(birthdateStartPathMock);

        Predicate birthdateStartPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.greaterThanOrEqualTo(birthdateStartPathMock, criteria.getBirthdateStart())).thenReturn(birthdateStartPredicateMock);

        Predicate returnedPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.and(birthdateStartPredicateMock)).thenReturn(returnedPredicateMock);

        Specification<Person> specification = PersonSpecification.findByCriteria(criteria);
        Predicate predicate = specification.toPredicate(personRootMock, criteriaQueryMock, criteriaBuilderMock);

        assertEquals(returnedPredicateMock, predicate);
    }

    @Test
    public void findByCriteria_whenHasBirthDateEndOnly_shouldReturnPredicate() {

        PersonSearchCriteria criteria = new PersonSearchCriteria(null, null, null, BIRTHDATE_END);

        Path birthdateEndPathMock = mock(Path.class);
        when(personRootMock.get("birthdate")).thenReturn(birthdateEndPathMock);

        Predicate birthdateEndPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.lessThanOrEqualTo(birthdateEndPathMock, criteria.getBirthdateEnd())).thenReturn(birthdateEndPredicateMock);

        Predicate returnedPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.and(birthdateEndPredicateMock)).thenReturn(returnedPredicateMock);

        Specification<Person> specification = PersonSpecification.findByCriteria(criteria);
        Predicate predicate = specification.toPredicate(personRootMock, criteriaQueryMock, criteriaBuilderMock);

        assertEquals(returnedPredicateMock, predicate);
    }

    @Test
    public void findByCriteria_whenHasBirthDateStartAndEndOnly_shouldReturnPredicate() {

        PersonSearchCriteria criteria = new PersonSearchCriteria(null, null, BIRTHDATE_START, BIRTHDATE_END);

        Path birthdatePathMock = mock(Path.class);
        when(personRootMock.get("birthdate")).thenReturn(birthdatePathMock);

        Predicate birthdateStartPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.greaterThanOrEqualTo(birthdatePathMock, criteria.getBirthdateStart())).thenReturn(birthdateStartPredicateMock);

        Predicate birthdateEndPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.lessThanOrEqualTo(birthdatePathMock, criteria.getBirthdateEnd())).thenReturn(birthdateEndPredicateMock);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(birthdateStartPredicateMock);
        predicates.add(birthdateEndPredicateMock);

        Predicate birthdateStartAndEndPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.and(birthdateStartPredicateMock, birthdateEndPredicateMock)).thenReturn(birthdateStartAndEndPredicateMock);

        Predicate returnedPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.and(birthdateStartAndEndPredicateMock)).thenReturn(returnedPredicateMock);

        Specification<Person> specification = PersonSpecification.findByCriteria(criteria);
        Predicate predicate = specification.toPredicate(personRootMock, criteriaQueryMock, criteriaBuilderMock);

        assertEquals(returnedPredicateMock, predicate);
    }
}
