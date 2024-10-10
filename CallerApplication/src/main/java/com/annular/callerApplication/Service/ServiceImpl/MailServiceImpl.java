package com.annular.callerApplication.Service.ServiceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annular.callerApplication.Service.MailService;
import com.annular.callerApplication.model.MailDetails;
import com.annular.callerApplication.repository.MailDetailsRepository;
import com.annular.callerApplication.webModel.MailDetailsWebModel;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
    MailDetailsRepository mailDetailsRepository;
	
	
	@Override
	public MailDetails saveMail(MailDetailsWebModel mailDetailsWebModel) {
	    MailDetails mailDetails = new MailDetails();
	    
	    // Map fields from MailDetailsWebModel to MailDetails
	    mailDetails.setEmailId(mailDetailsWebModel.getEmailId());
	    mailDetails.setIsActive(true);
	    mailDetails.setCreatedOn(LocalDateTime.now()); // Set createdOn to the current time
	    mailDetails.setUpdatedOn(LocalDateTime.now()); // Set updatedOn to the current time
//	    mailDetails.setCreatedBy(mailDetailsWebModel.getCreatedBy());
//	    mailDetails.setUpdatedBy(mailDetailsWebModel.getUpdatedBy());
	    
	    // Save the MailDetails object into MongoDB (assuming mailDetailsRepository is a MongoRepository)
	    return mailDetailsRepository.save(mailDetails);
	}


}
