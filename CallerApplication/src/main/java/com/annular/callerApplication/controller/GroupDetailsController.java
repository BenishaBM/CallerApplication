package com.annular.callerApplication.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping("/updateGroupDetailsById")
    public Response updateGroupDetailsById(@RequestBody GroupDetailsWebModel groupDetailsWebModel) {
        try {
            GroupResponse updatedGroupDetails = groupDetailsService.updateGroupDetailsById(groupDetailsWebModel);

            return new Response(0, "Update successful", updatedGroupDetails);
        } catch (IllegalArgumentException e) {
            return new Response(-1, "Group not found with ID: " + groupDetailsWebModel.getGroupId(), "");
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(), "");
        }
    }

    @PostMapping("/deleteGroupAndGroupDetails")
    public Response deleteGroupOrGroupDetails(
            @RequestParam("flag") int flag,
            @RequestParam("groupId") String groupId,
            @RequestParam(value = "groupDetailsId", required = false) String groupDetailsId) {
        try {
            if (flag == 0) {
                // Delete the group and associated group details
            	groupDetailsService.deleteGroupAndDetails(groupId);
                return new Response(0, "Success", "Group and associated details deleted successfully");
            } else if (flag == 1) {
                // Delete only the specific group detail
                if (groupDetailsId == null) {
                    return new Response(-1, "groupDetailsId is required for flag=1", null);
                }
                groupDetailsService.deleteGroupDetail(groupId, groupDetailsId);
                return new Response(0, "Success", "Group detail deleted successfully");
            } else {
                return new Response(-1, "Invalid flag value", null);
            }
        } catch (IllegalArgumentException e) {
            return new Response(-1, "Error: " + e.getMessage(), null);
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(), null);
        }
    }
    
    @GetMapping("/getGroupCodeByMobileNumber")
    public Response getGroupCodeByMobileNumber(@RequestParam("mobileNumber") String mobileNumber) {
        try {
            Optional<GroupResponse> savedGroupDetails = groupDetailsService.getGroupCodeByMobileNumber(mobileNumber);

            if (savedGroupDetails.isPresent()) {
                return new Response(0, "Retrieve data successfully", savedGroupDetails.get());
            } else {
                return new Response(-1, "mobileNumber not found with ID: " + mobileNumber, "");
            }

        } catch (IllegalArgumentException e) {
            return new Response(-1, "Error occurred while mobileNumber: " + e.getMessage(), "");
        } catch (Exception e) {
            return new Response(-1, "Unexpected error occurred: " + e.getMessage(), "");
        }
    }


}