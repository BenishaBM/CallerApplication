package com.annular.callerApplication.Service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

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

	    // Set createdBy and updatedBy fields if they exist in mailDetailsWebModel
	    mailDetails.setCreatedBy(mailDetailsWebModel.getCreatedBy());
	    mailDetails.setUpdatedBy(mailDetailsWebModel.getUpdatedBy());

	    // Map additional fields from MailDetailsWebModel to MailDetails
	    mailDetails.setQuotationData(mailDetailsWebModel.getQuotationData());
	    mailDetails.setAddressDetails(mailDetailsWebModel.getAddressDetails()); // Ensure this field is mapped
	    mailDetails.setPhoneNumber(mailDetailsWebModel.getPhoneNumber()); // Ensure this field is mapped
	    mailDetails.setNameData(mailDetailsWebModel.getNameData()); // Ensure this field is mapped

	    // Print debug information (optional)
	    System.out.println("Phone Number: " + mailDetailsWebModel.getPhoneNumber());
	    System.out.println("Name Data: " + mailDetailsWebModel.getNameData());

	    // Save the MailDetails object into MongoDB
	    return mailDetailsRepository.save(mailDetails);
	}

	@Override
	public List<MailDetails> getAllMail() {
	    return mailDetailsRepository.findAll();
	}

}
