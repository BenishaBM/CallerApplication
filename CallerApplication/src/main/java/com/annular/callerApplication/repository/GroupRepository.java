package com.annular.callerApplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.annular.callerApplication.model.Group;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

}
