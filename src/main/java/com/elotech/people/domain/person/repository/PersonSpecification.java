package com.elotech.people.domain.person.repository;

import com.elotech.people.domain.person.Person;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PersonSpecification {
    public static Specification<Person> findByCriteria(PersonSearchCriteria criteria) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(criteria.hasName()) {
                predicates.add(cb.like(cb.lower(root.get("name")), '%' + criteria.getName().toLowerCase() + '%'));
            }

            if(criteria.hasDocument()) {
                predicates.add(cb.equal(root.get("document"), criteria.getDocument()));
            }

            Predicate greaterThanOrEqualToBirthdateStart = cb.greaterThanOrEqualTo(root.get("birthdate"), criteria.getBirthdateStart());
            Predicate lessThanOrEqualToBirthdateEnd = cb.lessThanOrEqualTo(root.get("birthdate"), criteria.getBirthdateEnd());

            if(criteria.hasBirthDateStart() && !criteria.hasBirthDateEnd()) {
                predicates.add(greaterThanOrEqualToBirthdateStart);
            }

            if(!criteria.hasBirthDateStart() && criteria.hasBirthDateEnd()) {
                predicates.add(lessThanOrEqualToBirthdateEnd);
            }

            if(criteria.hasBirthDateStart() && criteria.hasBirthDateEnd()) {
                predicates.add(
                        cb.and(
                                greaterThanOrEqualToBirthdateStart,
                                lessThanOrEqualToBirthdateEnd
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[] {}));
        };
    }
}
