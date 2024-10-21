package com.annular.callerApplication.Service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	

	public static final Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);
	

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
	    // Trim and normalize sender and receiver numbers
	    senderNumber = senderNumber.trim();
	    receiverNumber = receiverNumber.trim();

	    if (!senderNumber.startsWith("+")) {
	        senderNumber = "+" + senderNumber;
	    }
	    if (!receiverNumber.startsWith("+")) {
	        receiverNumber = "+" + receiverNumber;
	    }

	    logger.info("Fetching notes for sender: {} and receiver: {}", senderNumber, receiverNumber);

	    // Fetching the latest note
	    List<NotesHistory> notesHistoryList = notesHistoryRepository
	            .findByGroupCodeAndReceiverNumberOrderByUpdatedOnDesc(groupCode, receiverNumber);

	    // Check if any notes were found
	    if (notesHistoryList != null && !notesHistoryList.isEmpty()) {
	        // Return the first (latest) note from the sorted list
	        return notesHistoryList.get(0);
	    } else {
	        // Handle the case when no notes are found
	        throw new IllegalArgumentException("No notes found for the given groupCode and receiverNumber.");
	    }
	}

	  @Override
	  public List<Map<String, String>> getNumberBySenderNumber(String senderNumber) {
	      // Trim the senderNumber to remove any leading/trailing whitespace
	      senderNumber = senderNumber.trim();
	      logger.info("Fetching receiver numbers for sender: {}", senderNumber);

	      // Normalize the senderNumber to handle cases with and without +
	      if (senderNumber.startsWith("%2B")) {
	          senderNumber = senderNumber.replace("%2B", "+"); // Decode %2B to +
	      } else if (!senderNumber.startsWith("+")) {
	          senderNumber = "+" + senderNumber; // Ensure it starts with +
	      }

	      // Fetching notes history by sender number
	      List<NotesHistory> notesList = notesHistoryRepository.findBySenderNumber(senderNumber);
	      logger.debug("Notes list retrieved: {}", notesList);

	      // Using a HashMap to hold notes history ID and receiver number
	      Map<String, String> receiverMap = new HashMap<>();
	      Set<String> uniqueReceiverNumbers = new HashSet<>();

	      // Check if the list is not empty
	      if (!notesList.isEmpty()) {
	          for (NotesHistory note : notesList) {
	              String notesHistoryId = note.getNotesHistoryId();
	              String receiverNumber = note.getReceiverNumber();

	              // Only add to map if the receiverNumber is not null or empty and is unique
	              if (receiverNumber != null && !receiverNumber.isEmpty() && uniqueReceiverNumbers.add(receiverNumber)) {
	                  receiverMap.put(notesHistoryId, receiverNumber);
	              }
	          }
	      } else {
	          logger.warn("No notes found for sender: {}", senderNumber);
	      }
	      // Convert the HashMap to a List of Maps to return
	      List<Map<String, String>> responseList = receiverMap.entrySet().stream()
	              .map(entry -> {
	                  Map<String, String> map = new HashMap<>();
	                  map.put("notesHistoryId", entry.getKey());
	                  map.put("receiverNumber", entry.getValue());
	                  return map;
	              })
	              .collect(Collectors.toList());

	      // Return the list of Maps containing notesHistoryId and receiverNumber
	      return responseList;
	  }

	  @Override
	  public List<NotesHistory> getNotesBySenderNumberAndReceiverNumbers(String senderNumber, String receiverNumber) {
	      // Trim the input parameters to remove any leading/trailing whitespace
	      senderNumber = senderNumber.trim();
	      receiverNumber = receiverNumber.trim();
	      
	      // Normalize the senderNumber and receiverNumber to handle cases with and without +
	      if (!senderNumber.startsWith("+")) {
	          senderNumber = "+" + senderNumber; // Ensure it starts with +
	      }
	      if (!receiverNumber.startsWith("+")) {
	          receiverNumber = "+" + receiverNumber; // Ensure it starts with +
	      }

	      logger.info("Fetching notes for sender: {} and receiver: {}", senderNumber, receiverNumber);

	      // Fetch the list of notes from the repository using the sender and receiver numbers
	      List<NotesHistory> notesHistoryList = notesHistoryRepository.findBySenderNumberAndReceiverNumber(senderNumber, receiverNumber);

	      // Check if the list is empty
	      if (notesHistoryList.isEmpty()) {
	          logger.warn("No notes found for sender: {} and receiver: {}", senderNumber, receiverNumber);
	      } else {
	          logger.debug("Notes retrieved: {}", notesHistoryList);
	      }

	      // Return the list of fetched NotesHistory objects
	      return notesHistoryList;
	  }

}
