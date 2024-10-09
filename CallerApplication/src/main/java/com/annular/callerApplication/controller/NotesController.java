package com.annular.callerApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    

}
