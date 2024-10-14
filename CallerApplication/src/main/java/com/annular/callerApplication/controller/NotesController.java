package com.annular.callerApplication.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annular.callerApplication.Response;
import com.annular.callerApplication.Service.NotesHistoryService;
import com.annular.callerApplication.model.Group;
import com.annular.callerApplication.model.NotesHistory;
import com.annular.callerApplication.webModel.GroupDetailsWebModel;
import com.annular.callerApplication.webModel.NotesWebModel;

@RestController
@RequestMapping("/notes")
public class NotesController {
	
	@Autowired
	NotesHistoryService notesHistoryServices;
	
    @PostMapping("/saveNotes")
    public Response saveNotes(@RequestBody NotesWebModel notesHistoryWebModel) { 
        try {
            NotesHistory savedGroupDetails = notesHistoryServices.saveNotes(notesHistoryWebModel);
            return new Response(0, "success","saveGroupDetails saved successfully.");
        } catch (IllegalArgumentException e) {
            return new Response(-1, "Error occurred while saveGroupDetails: " + e.getMessage(),"");
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(),"");
        } 
    }
    @GetMapping("/getNotes")
    public Response getNotes(@RequestParam("senderNumber") String senderNumber,
                             @RequestParam("receiverNumber") String receiverNumber,
                             @RequestParam("groupCode") String groupCode) {
        try {
            // Fetch notes based on senderNumber, receiverNumber, and groupCode
            NotesHistory notes = notesHistoryServices.getNotes(senderNumber, receiverNumber, groupCode);
            
            // Assuming 'notes' contains the fetched data
            return new Response(0, "Success", notes);
        } catch (IllegalArgumentException e) {
            // Handle specific case for invalid arguments
            return new Response(-1, "Error occurred while fetching notes: " + e.getMessage(), "");
        } catch (Exception e) {
            // Handle any unexpected errors
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(), "");
        }
    }

    @GetMapping("/getNumberBySenderNumber")
    public Response getNumberBySenderNumber(@RequestParam("senderNumber") String senderNumber) {
        try {
            // Fetch notes history by sender number
            List<Map<String, String>> notes = notesHistoryServices.getNumberBySenderNumber(senderNumber);

            if (notes != null) {
                // If notes are found, return success response
                return new Response(1, "Success", notes);
            } else {
                // If no notes are found, return a message indicating no data found
                return new Response(0, "Fail", "No notes found for the provided sender number");
            }
        } catch (IllegalArgumentException e) {
            // Handle specific case for invalid arguments
            return new Response(-1, "Error occurred while fetching notes: " + e.getMessage(), null);
        } catch (Exception e) {
            // Handle any unexpected errors
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(), null);
        }
    }

    @GetMapping("/getNotesBySenderNumberAndReceiverNumber")
    public Response getNotesBySenderNumberAndReceiverNumber(@RequestParam("senderNumber") String senderNumber,
                                 @RequestParam("receiverNumber") String receiverNumber) {
        try {
            // Fetch notes based on senderNumber and receiverNumber
            List<NotesHistory> notes = notesHistoryServices.getNotesBySenderNumberAndReceiverNumbers(senderNumber, receiverNumber);
            
            if (notes.isEmpty()) {
                return new Response(0, "No notes found for the provided sender and receiver numbers", notes);
            }

            // Assuming 'notes' contains the fetched data
            return new Response(0, "Success", notes);
        } catch (IllegalArgumentException e) {
            // Handle specific case for invalid arguments
            return new Response(-1, "Error occurred while fetching notes: " + e.getMessage(), "");
        } catch (Exception e) {
            // Handle any unexpected errors
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(), "");
        }
    }

}
