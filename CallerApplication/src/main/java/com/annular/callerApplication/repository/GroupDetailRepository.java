package com.annular.callerApplication.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.annular.callerApplication.model.Group;
import com.annular.callerApplication.model.GroupDetails;

@Repository
public interface GroupDetailRepository extends MongoRepository<GroupDetails, String> {

	List<GroupDetails> findByGroupId(String groupId);

	void deleteByGroupId(String groupId);

	void deleteByGroupIdAndGroupDetailsId(String groupId, String groupDetailsId);

}
