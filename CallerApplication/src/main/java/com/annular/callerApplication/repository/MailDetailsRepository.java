package com.annular.callerApplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.annular.callerApplication.model.MailDetails;

@Repository
public interface MailDetailsRepository extends MongoRepository<MailDetails, String> {

}
