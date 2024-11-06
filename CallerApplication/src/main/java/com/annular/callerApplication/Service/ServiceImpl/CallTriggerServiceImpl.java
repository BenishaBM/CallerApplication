package com.annular.callerApplication.Service.ServiceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annular.callerApplication.Service.CallTriggerService;
import com.annular.callerApplication.controller.CallTriggerWebModel;
import com.annular.callerApplication.model.CallTriggerList;
import com.annular.callerApplication.repository.CallTriggerRepository;

@Service
public class CallTriggerServiceImpl implements CallTriggerService {
	
	@Autowired
	CallTriggerRepository callTriggerListRepository;

	@Override
    public CallTriggerList saveCallTrigger(CallTriggerWebModel groupDetailsWebModel) {
        // Convert CallTriggerWebModel to CallTriggerList
        CallTriggerList callTriggerList = new CallTriggerList();
        
        // Setting the fields based on the incoming web model data
        callTriggerList.setTriggerId(groupDetailsWebModel.getTriggerId());  // Assuming getTriggerId exists
        callTriggerList.setSenderId(groupDetailsWebModel.getSenderId());    // Assuming getSenderId exists
        callTriggerList.setReceiverId(groupDetailsWebModel.getReceiverId());  // Assuming getReceiverId exists
        callTriggerList.setStatus(groupDetailsWebModel.getStatus());  // Assuming getStatus exists
        callTriggerList.setGroupCode(groupDetailsWebModel.getGroupCode());  // Assuming getGroupCode exists
        
        // Set the current timestamp for when the trigger occurs
        callTriggerList.setTriggerTime(LocalDateTime.now());

        // Save the CallTriggerList to the MongoDB repository
        return callTriggerListRepository.save(callTriggerList);
    }

}
