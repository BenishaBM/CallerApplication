package com.annular.callerApplication.Service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	    // Create a new Group entity
	    Group groupDetails = new Group();
	    groupDetails.setGroupName(groupDetailsWebModel.getGroupName());
	    groupDetails.setIsActive(groupDetailsWebModel.getIsActive());
	    groupDetails.setCreatedOn(LocalDateTime.now());
	    // Uncomment and use this if user details are needed
	    // UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    // groupDetails.setCreatedBy(Integer.parseInt(userDetails.getUsername()));

	    // Save the Group entity to the database
	    Group savedGroupDetails = groupRepository.save(groupDetails);

	    // Assuming you have a List of MobileNumberResponse in GroupDetailsWebModel
	    List<MobileNumberResponse> mobileNumbers = groupDetailsWebModel.getMobileNumber();

	    // Iterate over mobile numbers and save them in the GroupDetails table
	    for (MobileNumberResponse mobileNumberDTO : mobileNumbers) {
	        String mobileNumber = mobileNumberDTO.getMobileNumber(); // Get the mobile number from MobileNumberResponse
	        GroupDetails callerDetails = new GroupDetails();
	        callerDetails.setMobileNumbers(mobileNumber);
	        callerDetails.setGroupId(savedGroupDetails.getGroupId()); // Set the group ID
	        callerDetails.setCreatedOn(LocalDateTime.now());
	        callerDetails.setCreatedBy(groupDetailsWebModel.getCreatedBy());

	        // Save the GroupDetails entity to the database
	        groupDetailRepository.save(callerDetails);
	    }

	    // Return the saved Group entity
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
	    Logger logger = LoggerFactory.getLogger(getClass());
	    
	    logger.info("Starting update for groupId: {}", groupDetailsWebModel.getGroupId());

	    Optional<Group> existingGroupOpt = groupRepository.findById(groupDetailsWebModel.getGroupId());

	    if (existingGroupOpt.isPresent()) {
	        Group existingGroup = existingGroupOpt.get();
	        existingGroup.setGroupName(groupDetailsWebModel.getGroupName());
	        existingGroup.setIsActive(groupDetailsWebModel.getIsActive());
	        existingGroup.setUpdatedOn(LocalDateTime.now());
	        existingGroup.setUpdatedBy(groupDetailsWebModel.getUpdatedBy());
	        groupRepository.save(existingGroup);

	        List<GroupDetails> existingGroupDetails = groupDetailRepository.findByGroupId(groupDetailsWebModel.getGroupId());
	        Map<String, GroupDetails> existingDetailsMap = existingGroupDetails.stream()
	                .collect(Collectors.toMap(GroupDetails::getGroupDetailsId, Function.identity()));

	        logger.info("Existing Group Details: {}", existingDetailsMap);

	        for (MobileNumberResponse mobileNumberDTO : groupDetailsWebModel.getMobileNumber()) {
	            String mobileNumber = mobileNumberDTO.getMobileNumber(); // Log here
	            String groupDetailsId = mobileNumberDTO.getGroupDetailsId(); // Log here

	            logger.info("Processing MobileNumberResponse - mobileNumber: {}, groupDetailsId: {}", mobileNumber, groupDetailsId);

	            if (groupDetailsId != null) {
	                GroupDetails existingDetail = existingDetailsMap.get(groupDetailsId);
	                if (existingDetail != null) {
	                    logger.info("Found existing GroupDetails with ID: {}", groupDetailsId);
	                    existingDetail.setMobileNumbers(mobileNumber);
	                    existingDetail.setUpdatedOn(LocalDateTime.now());
	                    existingDetail.setUpdatedBy(groupDetailsWebModel.getUpdatedBy());
	                    groupDetailRepository.save(existingDetail);
	                    logger.info("Updated existing GroupDetails - groupDetailsId: {}, mobileNumber: {}", groupDetailsId, mobileNumber);
	                } else {
	                    logger.warn("GroupDetails with ID {} not found for update", groupDetailsId);
	                }
	            } else {
	                logger.info("Creating new GroupDetails with mobileNumber: {}", mobileNumber);
	                GroupDetails newGroupDetails = new GroupDetails();
	                newGroupDetails.setMobileNumbers(mobileNumber);
	                newGroupDetails.setGroupId(existingGroup.getGroupId());
	                newGroupDetails.setCreatedOn(LocalDateTime.now());
	                newGroupDetails.setCreatedBy(groupDetailsWebModel.getCreatedBy());
	                groupDetailRepository.save(newGroupDetails);
	                logger.info("Created new GroupDetails with mobileNumber: {}", mobileNumber);
	            }
	        }

	        List<MobileNumberResponse> mobileNumberResponses = existingGroupDetails.stream()
	            .map(detail -> {
	                String mobileNumber = detail.getMobileNumbers(); // Log here
	                String groupDetailsId = detail.getGroupDetailsId(); // Log here
	                logger.info("Mapping GroupDetails to MobileNumberResponse - mobileNumber: {}, groupDetailsId: {}", mobileNumber, groupDetailsId);
	                return new MobileNumberResponse(mobileNumber, groupDetailsId);
	            })
	            .collect(Collectors.toList());

	        GroupResponse groupResponse = new GroupResponse();
	        groupResponse.setGroupId(existingGroup.getGroupId());
	        groupResponse.setGroupName(existingGroup.getGroupName());
	        groupResponse.setGroupStatus(existingGroup.getIsActive());
	        groupResponse.setMobileNumbers(mobileNumberResponses);

	        logger.info("Update successful for groupId: {}", existingGroup.getGroupId());
	        return groupResponse;
	    } else {
	        throw new IllegalArgumentException("Group with ID " + groupDetailsWebModel.getGroupId() + " does not exist.");
	    }
	}



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