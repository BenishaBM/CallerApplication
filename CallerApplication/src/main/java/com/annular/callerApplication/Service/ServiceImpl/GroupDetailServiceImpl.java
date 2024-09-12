package com.annular.callerApplication.Service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.annular.callerApplication.Service.GroupDetailsService;
import com.annular.callerApplication.model.Group;
import com.annular.callerApplication.model.GroupDetails;
import com.annular.callerApplication.repository.GroupDetailRepository;
import com.annular.callerApplication.repository.GroupRepository;
import com.annular.callerApplication.webModel.GroupDetailsWebModel;
import com.annular.callerApplication.webModel.GroupResponse;
import com.annular.callerApplication.webModel.MobileNumberResponse;

@Service
public class GroupDetailServiceImpl implements GroupDetailsService {

	@Autowired
	GroupDetailRepository groupDetailRepository;

	@Autowired
	GroupRepository groupRepository;
	
//    @Autowired
//    UserDetails userDetails;

	@Override
	public Group saveGroupDetails(GroupDetailsWebModel groupDetailsWebModel) {

		Group groupDetails = new Group();

		groupDetails.setGroupName(groupDetailsWebModel.getGroupName());
		groupDetails.setIsActive(groupDetailsWebModel.getIsActive());
		groupDetails.setCreatedOn(LocalDateTime.now());
		//groupDetails.setCreatedBy(userDetails.userInfo().getId());
	    // Retrieve the current authenticated user details
//	    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//	    groupDetails.setCreatedBy(Integer.parseInt(userDetails.getUsername()));
		
		// Save the GroupDetails entity to the database
		Group savedGroupDetails = groupRepository.save(groupDetails);

		// Assuming you have a List of mobile numbers in GroupDetailsWebModel
		List<String> mobileNumbers = groupDetailsWebModel.getMobileNumber();

		// Iterate over mobile numbers and save them in the CallerDetails table
		for (String mobileNumber : mobileNumbers) {
			GroupDetails callerDetails = new GroupDetails();
			callerDetails.setMobileNumber(mobileNumber);
			callerDetails.setGroupId(savedGroupDetails.getGroupId()); // Set the group ID
			callerDetails.setCreatedOn(LocalDateTime.now());
			callerDetails.setCreatedBy(groupDetailsWebModel.getCreatedBy());

			// Save the CallerDetails entity to the database
			groupDetailRepository.save(callerDetails);
		}

		// Return the saved GroupDetails entity
		return savedGroupDetails;
	}


	@Override
	public List<GroupResponse> getAllGroupDetails() {

	    // 1. Fetch all groups from the Group collection
	    List<Group> groups = groupRepository.findAll();

	    // Create a list to hold the response for each group
	    List<GroupResponse> groupResponses = new ArrayList<>();

	    // 2. Iterate over each group
	    for (Group group : groups) {
	        // Create a new response object for each group
	        GroupResponse groupResponse = new GroupResponse();
	        groupResponse.setGroupName(group.getGroupName());
	        groupResponse.setGroupStatus(group.getIsActive());

	        // 3. Fetch associated mobile numbers for the current group from GroupDetails
	        List<GroupDetails> groupDetailsList = groupDetailRepository.findByGroupId(group.getGroupId());

	        // Create a list to hold the mobile numbers
	        List<MobileNumberResponse> mobileNumbers = new ArrayList<>();
	        for (GroupDetails details : groupDetailsList) {
	            MobileNumberResponse mobileNumberResponse = new MobileNumberResponse();
	            mobileNumberResponse.setMobileNumber(details.getMobileNumber());
	            mobileNumbers.add(mobileNumberResponse);
	        }

	        // Set the mobile numbers in the group response
	        groupResponse.setMobileNumbers(mobileNumbers);

	        // Add the group response to the final list
	        groupResponses.add(groupResponse);
	    }

	    // 4. Return the list of group responses
	    return groupResponses;
	}


}
