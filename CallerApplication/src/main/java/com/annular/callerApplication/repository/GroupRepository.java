package com.annular.callerApplication.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.annular.callerApplication.model.Group;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

	void deleteByGroupId(String groupId);

	 @Query("{ 'groupName': ?0 }")
	Optional<Group> findByGroupName(String groupName);

}
