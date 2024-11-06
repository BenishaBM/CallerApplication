package com.annular.callerApplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.annular.callerApplication.model.CallTriggerList;


@Repository
public interface CallTriggerRepository extends MongoRepository<CallTriggerList, String>{

}
