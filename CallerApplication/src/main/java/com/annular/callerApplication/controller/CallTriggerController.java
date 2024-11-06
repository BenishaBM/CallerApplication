package com.annular.callerApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annular.callerApplication.Response;
import com.annular.callerApplication.Service.CallTriggerService;
import com.annular.callerApplication.model.CallTriggerList;

@RestController
@RequestMapping("/callerTrigger")
public class CallTriggerController {
	
	@Autowired
	CallTriggerService callTriggerService;
	
	@PostMapping("/saveCallTrigger")
	public Response saveCallTrigger(@RequestBody CallTriggerWebModel groupDetailsWebModel) { 
        try {
        	CallTriggerList savedGroupDetails = callTriggerService.saveCallTrigger(groupDetailsWebModel);
            return new Response(0, "success","saveCallTrigger saved successfully.");
        } catch (IllegalArgumentException e) {
            return new Response(-1, "Error occurred while saveGroupDetails: " + e.getMessage(),"");
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(),"");
        } 
    }
    

}
