package com.annular.callerApplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.annular.callerApplication.model.NotesHistory;

@Repository
public interface NotesHistoryRepository extends MongoRepository<NotesHistory, String> {

	NotesHistory save(NotesHistory notesHistory);

    @Query("{ 'groupCode': ?0, 'receiverNumber': ?1 }")
	Optional<NotesHistory> findFirstByGroupCodeAndReceiverNumberOrderByUpdatedOnDesc(String groupCode,
			String receiverNumber);

    // Existing query method

    List<NotesHistory> findBySenderNumber(String senderNumber);

}
