package com.annular.callerApplication.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annular.callerApplication.Response;
import com.annular.callerApplication.Service.GroupDetailsService;
import com.annular.callerApplication.model.Group;
import com.annular.callerApplication.webModel.GroupDetailsWebModel;
import com.annular.callerApplication.webModel.GroupResponse;

@RestController
@RequestMapping("/group")
public class GroupDetailsController {
	
	@Autowired
	GroupDetailsService groupDetailsService;
	
	
    @PostMapping("/saveGroupDetails")
    public Response saveGroupDetails(@RequestBody GroupDetailsWebModel groupDetailsWebModel) { 
        try {
            Group savedGroupDetails = groupDetailsService.saveGroupDetails(groupDetailsWebModel);
            return new Response(0, "success","saveGroupDetails saved successfully.");
        } catch (IllegalArgumentException e) {
            return new Response(-1, "Error occurred while saveGroupDetails: " + e.getMessage(),"");
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(),"");
        } 
    }
    
    @GetMapping("/getGroupDetails")
    public Response getGroupDetails() { 
        try {
            List<GroupResponse> savedGroupDetails = groupDetailsService.getAllGroupDetails();
            return new Response(0, "reterieve data successfully.",savedGroupDetails);
        } catch (IllegalArgumentException e) {
            return new Response(-1, "Error occurred while getGroupDetails: " + e.getMessage(),"");
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(),"");
        } 
    }
    
    @GetMapping("/getGroupDetailsById")
    public Response getGroupDetailsById(@RequestParam("groupId") String groupId) {
        try {
            Optional<GroupResponse> savedGroupDetails = groupDetailsService.getGroupDetailsById(groupId);

            if (savedGroupDetails.isPresent()) {
                return new Response(0, "Retrieve data successfully", savedGroupDetails.get());
            } else {
                return new Response(-1, "Group not found with ID: " + groupId, "");
            }

        } catch (IllegalArgumentException e) {
            return new Response(-1, "Error occurred while getGroupDetailsById: " + e.getMessage(), "");
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(), "");
        }
    }


}
