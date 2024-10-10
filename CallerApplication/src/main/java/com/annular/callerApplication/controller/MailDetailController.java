package com.annular.callerApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annular.callerApplication.Response;
import com.annular.callerApplication.Service.MailService;
import com.annular.callerApplication.model.MailDetails;
import com.annular.callerApplication.model.NotesHistory;
import com.annular.callerApplication.webModel.MailDetailsWebModel;
import com.annular.callerApplication.webModel.NotesWebModel;

@RestController
@RequestMapping("/Mail")
public class MailDetailController {
	
	@Autowired
	MailService mailService;
	
    @PostMapping("/saveMail")
    public Response saveMail(@RequestBody MailDetailsWebModel mailDetailsWebModel) { 
        try {
            MailDetails savedGroupDetails = mailService.saveMail(mailDetailsWebModel);
            return new Response(1, "success","saveMail saved successfully.");
        } catch (IllegalArgumentException e) {
            return new Response(-1, "Error occurred while saveMail: " + e.getMessage(),"");
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(),"");
        } 
    }

}
