package com.annular.callerApplication.Service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annular.callerApplication.Service.NotesHistoryService;
import com.annular.callerApplication.model.NotesHistory;
import com.annular.callerApplication.repository.NotesHistoryRepository;
import com.annular.callerApplication.webModel.NotesWebModel;

@Service
public class NotesServiceImpl implements NotesHistoryService{
	
	@Autowired
	NotesHistoryRepository notesHistoryRepository;

	@Override
	public NotesHistory saveNotes(NotesWebModel notesHistoryWebModel) {
	    // Validate input
	    if (notesHistoryWebModel == null) {
	        throw new IllegalArgumentException("Notes data cannot be null.");
	    }
	    if (notesHistoryWebModel.getSenderNumber() == null || notesHistoryWebModel.getReceiverNumber() == null) {
	        throw new IllegalArgumentException("Sender and Receiver numbers cannot be null.");
	    }

	    // Create a new NotesHistory entity
	    NotesHistory notesHistory = new NotesHistory();
	    notesHistory.setSenderNumber(notesHistoryWebModel.getSenderNumber());
	    notesHistory.setGroupCode(notesHistoryWebModel.getGroupCode());
	    notesHistory.setReceiverNumber(notesHistoryWebModel.getReceiverNumber());
	    notesHistory.setIsActive("true"); // Assuming this is a string; change as necessary
	    notesHistory.setCreatedOn(LocalDateTime.now());
	    notesHistory.setCreatedBy(notesHistoryWebModel.getCreatedBy());
	    notesHistory.setNotes(notesHistoryWebModel.getNotes()); // Assuming NotesWebModel has a method to get notes

	    // Save the NotesHistory entity to the database
	    NotesHistory savedNotesHistory = notesHistoryRepository.save(notesHistory);

	    // Return the saved NotesHistory entity
	    return savedNotesHistory;
	}

	  @Override
	    public NotesHistory getNotes(String senderNumber, String receiverNumber, String groupCode) {
	        // Fetch the latest note for the given groupCode and receiverNumber
	        Optional<NotesHistory> notesHistoryOptional = notesHistoryRepository
	                .findFirstByGroupCodeAndReceiverNumberOrderByUpdatedOnDesc(groupCode, receiverNumber);

	        // Check if a note was found, otherwise throw an exception or return null
	        if (notesHistoryOptional.isPresent()) {
	            return notesHistoryOptional.get();
	        } else {
	            // You can throw an exception here or handle the case when no notes are found
	            throw new IllegalArgumentException("No notes found for the given groupCode and receiverNumber.");
	        }
	    }
}
