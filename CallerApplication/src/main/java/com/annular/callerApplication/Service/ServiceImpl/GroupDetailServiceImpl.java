package com.annular.callerApplication.Service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		// groupDetails.setCreatedBy(userDetails.userInfo().getId());
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
			callerDetails.setMobileNumbers(mobileNumber);
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
			groupResponse.setGroupId(group.getGroupId());
			groupResponse.setGroupName(group.getGroupName());
			groupResponse.setGroupStatus(group.getIsActive());

			// 3. Fetch associated mobile numbers for the current group from GroupDetails
			List<GroupDetails> groupDetailsList = groupDetailRepository.findByGroupId(group.getGroupId());

			// Create a list to hold the mobile numbers
			List<MobileNumberResponse> mobileNumbers = new ArrayList<>();
			for (GroupDetails details : groupDetailsList) {
				MobileNumberResponse mobileNumberResponse = new MobileNumberResponse();
				mobileNumberResponse.setMobileNumber(details.getMobileNumbers());
				mobileNumberResponse.setGroupDetailsId(details.getGroupDetailsId());
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

	@Override
	public Optional<GroupResponse> getGroupDetailsById(String groupId) {

		// Fetch the group by groupId
		Optional<Group> groupOptional = groupRepository.findById(groupId);

		if (!groupOptional.isPresent()) {
			return Optional.empty();
		}

		Group group = groupOptional.get();

		// Create and populate the GroupResponse
		GroupResponse groupResponse = new GroupResponse();
		groupResponse.setGroupName(group.getGroupName());
		groupResponse.setGroupStatus(group.getIsActive());
		groupResponse.setGroupId(group.getGroupId());

		// Fetch associated mobile numbers
		List<GroupDetails> groupDetailsList = groupDetailRepository.findByGroupId(group.getGroupId());

		List<MobileNumberResponse> mobileNumbers = new ArrayList<>();
		for (GroupDetails details : groupDetailsList) {
			MobileNumberResponse mobileNumberResponse = new MobileNumberResponse();
			mobileNumberResponse.setMobileNumber(details.getMobileNumbers());
			mobileNumberResponse.setGroupDetailsId(details.getGroupDetailsId());
			mobileNumbers.add(mobileNumberResponse);
		}

		groupResponse.setMobileNumbers(mobileNumbers);

		return Optional.of(groupResponse);
	}
	@Override
	public GroupResponse updateGroupDetailsById(GroupDetailsWebModel groupDetailsWebModel) {
	    // Step 1: Check if the groupId exists in the Group table
	    Optional<Group> existingGroupOpt = groupRepository.findById(groupDetailsWebModel.getGroupId());

	    if (existingGroupOpt.isPresent()) {
	        // Update the existing Group entity
	        Group existingGroup = existingGroupOpt.get();
	        existingGroup.setGroupName(groupDetailsWebModel.getGroupName());
	        existingGroup.setIsActive(groupDetailsWebModel.getIsActive());
	        existingGroup.setUpdatedOn(LocalDateTime.now());
	        existingGroup.setUpdatedBy(groupDetailsWebModel.getUpdatedBy());

	        // Save the updated Group entity
	        groupRepository.save(existingGroup);

	        // Step 2: Check if the GroupDetails entries exist for the given groupId
	        List<GroupDetails> groupDetailsList = groupDetailRepository.findByGroupId(groupDetailsWebModel.getGroupId());

	        List<String> newMobileNumbers = groupDetailsWebModel.getMobileNumber();
	        for (String mobileNumber : newMobileNumbers) {
	            // Check if a groupDetailsId is provided to update the existing mobile number
	            Optional<GroupDetails> existingGroupDetailsOpt = groupDetailsList.stream()
	                .filter(groupDetail -> groupDetailsWebModel.getGropuDetailsId() != null 
	                    && groupDetailsWebModel.getGropuDetailsId().equals(groupDetail.getGroupDetailsId()))
	                .findFirst();

	            if (existingGroupDetailsOpt.isPresent()) {
	                // Update the existing GroupDetails entry
	                GroupDetails existingGroupDetails = existingGroupDetailsOpt.get();
	                existingGroupDetails.setMobileNumbers(mobileNumber);
	                existingGroupDetails.setUpdatedOn(LocalDateTime.now());
	                existingGroupDetails.setUpdatedBy(groupDetailsWebModel.getUpdatedBy());

	                // Save the updated GroupDetails entry
	                groupDetailRepository.save(existingGroupDetails);
	            } else {
	                // Check if the mobile number already exists
	                boolean mobileNumberExists = groupDetailsList.stream()
	                    .anyMatch(groupDetail -> groupDetail.getMobileNumbers().equals(mobileNumber));

	                if (!mobileNumberExists) {
	                    // Create a new GroupDetails entry if the mobile number is not found
	                    GroupDetails newGroupDetails = new GroupDetails();
	                    newGroupDetails.setMobileNumbers(mobileNumber);
	                    newGroupDetails.setGroupId(existingGroup.getGroupId());
	                    newGroupDetails.setCreatedOn(LocalDateTime.now());
	                    newGroupDetails.setCreatedBy(groupDetailsWebModel.getCreatedBy());

	                    // Save the new GroupDetails entry
	                    groupDetailRepository.save(newGroupDetails);
	                }
	            }
	        }

	        // Step 3: Create and return GroupResponse
	        GroupResponse groupResponse = new GroupResponse();
	        groupResponse.setGroupId(existingGroup.getGroupId());
	        groupResponse.setGroupName(existingGroup.getGroupName());
	        groupResponse.setGroupStatus(existingGroup.getIsActive());
	        groupResponse.setMobileNumbers(groupDetailsList.stream()
	            .map(detail -> new MobileNumberResponse(detail.getMobileNumbers(), detail.getGroupDetailsId())) // Assuming MobileNumberResponse has a constructor that takes a mobile number
	            .collect(Collectors.toList()));

	        return groupResponse;
	    } else {
	        // Handle the case where the groupId does not exist
	        throw new IllegalArgumentException("Group with ID " + groupDetailsWebModel.getGroupId() + " does not exist.");
	    }
	}


//	@Override
//	public GroupResponse updateGroupDetailsById(GroupDetailsWebModel groupDetailsWebModel) {
//	    // Step 1: Check if the groupId exists in the Group table
//	    Optional<Group> existingGroupOpt = groupRepository.findById(groupDetailsWebModel.getGroupId());
//
//	    if (existingGroupOpt.isPresent()) {
//	        // Update the existing Group entity
//	        Group existingGroup = existingGroupOpt.get();
//	        existingGroup.setGroupName(groupDetailsWebModel.getGroupName());
//	        existingGroup.setIsActive(groupDetailsWebModel.getIsActive());
//	        existingGroup.setUpdatedOn(LocalDateTime.now());
//	        existingGroup.setUpdatedBy(groupDetailsWebModel.getUpdatedBy());
//
//	        // Save the updated Group entity
//	        groupRepository.save(existingGroup);
//
//	        // Step 2: Check if the GroupDetails entries exist for the given groupId
//	        List<GroupDetails> groupDetailsList = groupDetailRepository.findByGroupId(groupDetailsWebModel.getGroupId());
//
//	        List<String> newMobileNumbers = groupDetailsWebModel.getMobileNumber();
//	        for (String mobileNumber : newMobileNumbers) {
//	            // Check if the mobile number already exists
//	            boolean mobileNumberExists = groupDetailsList.stream()
//	                .anyMatch(groupDetail -> groupDetail.getMobileNumbers().equals(mobileNumber));
//
//	            if (!mobileNumberExists) {
//	                // Create a new GroupDetails entry if the mobile number is not found
//	                GroupDetails newGroupDetails = new GroupDetails();
//	                newGroupDetails.setMobileNumbers(mobileNumber);
//	                newGroupDetails.setGroupId(existingGroup.getGroupId());
//	                newGroupDetails.setCreatedOn(LocalDateTime.now());
//	                newGroupDetails.setCreatedBy(groupDetailsWebModel.getCreatedBy());
//
//	                // Save the new GroupDetails entry
//	                groupDetailRepository.save(newGroupDetails);
//	            }
//	        }
//
//	        // Step 3: Create and return GroupResponse
//	        GroupResponse groupResponse = new GroupResponse();
//	        groupResponse.setGroupId(existingGroup.getGroupId());
//	        groupResponse.setGroupName(existingGroup.getGroupName());
//	        groupResponse.setGroupStatus(existingGroup.getIsActive());
//	        groupResponse.setMobileNumbers(groupDetailsList.stream()
//	            .map(detail -> new MobileNumberResponse(detail.getMobileNumbers(), detail.getGroupDetailsId())) // Assuming MobileNumberResponse has a constructor that takes a mobile number
//	            .collect(Collectors.toList()));
//
//	        return groupResponse;
//	    } else {
//	        // Handle the case where the groupId does not exist
//	        throw new IllegalArgumentException("Group with ID " + groupDetailsWebModel.getGroupId() + " does not exist.");
//	    }
//	}

    public void deleteGroupAndDetails(String groupId) {
        // Check if group exists in the Group collection
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()) {
            // Delete associated GroupDetails entries first to avoid any issues
            groupDetailRepository.deleteByGroupId(groupId);
            
            // Delete the Group itself
            groupRepository.deleteById(groupId);
        } else {
            throw new IllegalArgumentException("Group not found with ID: " + groupId);
        }
    }

    public void deleteGroupDetail(String groupId, String groupDetailsId) {
        // Delete specific group detail
        groupDetailRepository.deleteByGroupIdAndGroupDetailsId(groupId, groupDetailsId);
    }

	
}