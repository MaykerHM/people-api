package com.elotech.people.domain.contact.repository;

import com.elotech.people.domain.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID>, JpaSpecificationExecutor {
}
