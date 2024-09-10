package com.annular.callerApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.annular.callerApplication.Response;
import com.annular.callerApplication.Service.CallerDetailsService;
import com.annular.callerApplication.model.CallerDetails;
import com.annular.callerApplication.webModel.CallerDetailsWebModel;


@RestController
@RequestMapping("/caller")
public class CallerDetailsController {

    @Autowired
    private CallerDetailsService callerDetailsService;

    @RequestMapping(path = "/save", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response saveCaller(@ModelAttribute CallerDetailsWebModel callerDetailsWebModel) { 
        try {
            CallerDetails savedCallerDetails = callerDetailsService.saveCallerDetails(callerDetailsWebModel);
            return new Response(0, "Caller details saved successfully.","");
        } catch (IllegalArgumentException e) {
            return new Response(-1, "Error occurred while saving caller details: " + e.getMessage(),"");
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(),"");
        } 
    }

    // GET API to retrieve caller details by ID
    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getCallerDetails(@PathVariable("id") String id) {
        try {
            CallerDetails callerDetails = callerDetailsService.getCallerDetailsById(id);
            if (callerDetails != null) {
                return new Response(0, "Caller details retrieved successfully.", callerDetails);
            } else {
                return new Response(-1, "Caller details not found.", "");
            }
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(), "");
        }
    }
    

   @GetMapping("getAllActiveDetails")
    public Response getAllActiveDetails() {
        try {
            List<CallerDetails> callerDetails = callerDetailsService.getAllActiveDetails();
            if (callerDetails != null) {
                return new Response(0, "Caller details retrieved successfully.", callerDetails);
            } else {
                return new Response(-1, "Caller details not found.", "");
            }
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(), "");
        }
    }
}



