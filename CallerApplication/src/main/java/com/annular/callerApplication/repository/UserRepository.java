package com.annular.callerApplication.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.annular.callerApplication.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

	Optional<User> findByEmailId(String email);

	Optional<User> findByEmailIdAndUserType(String emailId, String userType);

}
