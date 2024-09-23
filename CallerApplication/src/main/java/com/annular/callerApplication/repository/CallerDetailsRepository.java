package com.annular.callerApplication.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.annular.callerApplication.model.CallerDetails;

@Repository
public interface CallerDetailsRepository extends MongoRepository<CallerDetails, String>{

	// Custom query to get all caller details where isActive is true
    @Query("{ 'isActive' : true }")
	List<CallerDetails> getAllActiveStatus();

	List<CallerDetails> findBySenderNumber(String senderNumber);

}
