package com.annular.callerApplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.annular.callerApplication.model.NotesHistory;

@Repository
public interface NotesHistoryRepository extends MongoRepository<NotesHistory, String> {

	NotesHistory save(NotesHistory notesHistory);

//    @Query("{ 'groupCode': ?0, 'receiverNumber': ?1 }")
//	Optional<NotesHistory> findFirstByGroupCodeAndReceiverNumberOrderByUpdatedOnDesc(String groupCode,
//			String receiverNumber);

    // Existing query method

    List<NotesHistory> findBySenderNumber(String senderNumber);

    List<NotesHistory> findBySenderNumberAndReceiverNumber(String senderNumber, String receiverNumber);

//    @Query("SELECT nh FROM NotesHistory nh WHERE nh.groupCode = :groupCode AND nh.receiverNumber = :receiverNumber ORDER BY nh.createdOn DESC")
//    List<NotesHistory> findByGroupCodeAndReceiverNumberOrderByUpdatedOnDesc(
//        @Param("groupCode") String groupCode, 
//        @Param("receiverNumber") String receiverNumber
//    );

    
//	List<NotesHistory> findByGroupCodeAndReceiverNumberAndSenderNumberOrderByUpdatedOnDesc(String groupCode,
//			String receiverNumber, String senderNumber);
    @Query(value = "{ 'groupCode': ?0, 'receiverNumber': ?1 }", sort = "{ 'createdOn': -1 }")
    List<NotesHistory> findByGroupCodeAndReceiverNumberSorted(String groupCode, String receiverNumber);




	//List<NotesHistory> findByGroupCodeAndReceiverNumberOrderByUpdatedOnDesc(String groupCode, String receiverNumber);
}
