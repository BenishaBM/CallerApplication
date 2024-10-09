package com.annular.callerApplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.annular.callerApplication.model.NotesHistory;

@Repository
public interface NotesHistoryRepository extends MongoRepository<NotesHistory, String> {

	NotesHistory save(NotesHistory notesHistory);

}