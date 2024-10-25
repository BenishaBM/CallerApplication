package com.annular.callerApplication.Service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.annular.callerApplication.Service.MailService;
import com.annular.callerApplication.model.MailDetails;
import com.annular.callerApplication.model.QuotationData;
import com.annular.callerApplication.repository.MailDetailsRepository;
import com.annular.callerApplication.repository.QuotationDataRepository;
import com.annular.callerApplication.webModel.MailDetailsWebModel;
import com.annular.callerApplication.webModel.QuotationDataWebModel;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
    MailDetailsRepository mailDetailsRepository;
	
	@Autowired
	QuotationDataRepository quotationDataRepository;
	
	@Override
	public MailDetails saveMail(MailDetailsWebModel mailDetailsWebModel) {
	    MailDetails mailDetails = new MailDetails();
	    
	    // Map fields from MailDetailsWebModel to MailDetails
	    mailDetails.setEmailId(mailDetailsWebModel.getEmailId());
	    mailDetails.setIsActive(true);
	    mailDetails.setCreatedOn(LocalDateTime.now());
	    mailDetails.setUpdatedOn(LocalDateTime.now());
	    
	    // Set createdBy and updatedBy fields
	    mailDetails.setCreatedBy(mailDetailsWebModel.getCreatedBy());
	    mailDetails.setUpdatedBy(mailDetailsWebModel.getUpdatedBy());
	    
	    // Handle the mapping of quotationData
	    if (mailDetailsWebModel.getQuotationData() != null) {
	        QuotationData quotationData = new QuotationData();
	        QuotationDataWebModel quotationDataWebModel = mailDetailsWebModel.getQuotationData();

	        // Map fields from QuotationDataWebModel to QuotationData
	        quotationData.setServiceType(quotationDataWebModel.getServiceType());
	        quotationData.setCost(quotationDataWebModel.getCost());
	        quotationData.setWeight(quotationDataWebModel.getWeight());
	        quotationData.setDimensions(quotationDataWebModel.getDimensions());
	        quotationData.setEstimatedDeliveryTime(quotationDataWebModel.getEstimatedDeliveryTime());
	        quotationData.setTransitTime(quotationDataWebModel.getTransitTime());
	        quotationData.setValidity(quotationDataWebModel.getValidity());

	        // Save the QuotationData object into MongoDB
	        try {
	            // Save quotation data first
	            QuotationData savedQuotationData = quotationDataRepository.save(quotationData);
	            // Set the saved QuotationData ID back to mailDetails
	            mailDetails.setQuotationData(savedQuotationData); // This sets the entire QuotationData object
	        } catch (Exception e) {
	            System.err.println("Error saving quotation data: " + e.getMessage());
	            // Handle the error (log it, rethrow, etc.)
	            return null; // or throw a custom exception
	        }
	    }

	    // Map additional fields
	    mailDetails.setAddressDetails(mailDetailsWebModel.getAddressDetails());
	    mailDetails.setPhoneNumber(mailDetailsWebModel.getPhoneNumber());
	    mailDetails.setNameData(mailDetailsWebModel.getNameData());

	    // Optional debug information
	    System.out.println("Phone Number: " + mailDetailsWebModel.getPhoneNumber());
	    System.out.println("Name Data: " + mailDetailsWebModel.getNameData());

	    // Save the MailDetails object into MongoDB and handle exceptions
	    try {
	        return mailDetailsRepository.save(mailDetails);
	    } catch (Exception e) {
	        // Handle the error (log it, rethrow, etc.)
	        System.err.println("Error saving mail details: " + e.getMessage());
	        return null; // or throw a custom exception
	    }
	}


	
	public ResponseEntity<Map<String, Object>> getAllMail() {
	    List<MailDetails> mailDetailsList = mailDetailsRepository.findAll(); // or your specific query

	    List<Map<String, Object>> responseData = mailDetailsList.stream().map(mailDetails -> {
	        Map<String, Object> formattedData = new HashMap<>();
	        formattedData.put("emailId", mailDetails.getEmailId());
	        formattedData.put("isActive", mailDetails.getIsActive());
	        formattedData.put("createdOn", mailDetails.getCreatedOn());
	        formattedData.put("updatedOn", mailDetails.getUpdatedOn());
	        formattedData.put("createdBy", mailDetails.getCreatedBy());
	        formattedData.put("updatedBy", mailDetails.getUpdatedBy());
	        formattedData.put("addressDetails", mailDetails.getAddressDetails());
	        formattedData.put("nameData", mailDetails.getNameData());
	        formattedData.put("phoneNumber", mailDetails.getPhoneNumber() != null ? mailDetails.getPhoneNumber() : "N/A");

	        // Handle the quotationData formatting
	        if (mailDetails.getQuotationData() != null) {
	            QuotationData quotationData = mailDetails.getQuotationData();
	            Map<String, Object> formattedQuotationData = new HashMap<>();
	            formattedQuotationData.put("Service Type", quotationData.getServiceType() != null ? quotationData.getServiceType() : "Service Type: N/A");
	            formattedQuotationData.put("Cost", quotationData.getCost());
	            formattedQuotationData.put("Weight", quotationData.getWeight());
	            formattedQuotationData.put("Dimensions", quotationData.getDimensions());
	            formattedQuotationData.put("Estimated Delivery Time", quotationData.getEstimatedDeliveryTime() != null ? quotationData.getEstimatedDeliveryTime() : "N/A");
	            formattedQuotationData.put("Transit Time", quotationData.getTransitTime() != null ? quotationData.getTransitTime() : "N/A");
	            formattedQuotationData.put("Validity", quotationData.getValidity());

	            // Summarized information with correction
	            formattedQuotationData.put("Summarized", String.format("Service Type: %s. Cost: %s. Weight: %s. Transit Time: %s.", 
	                formattedQuotationData.get("Service Type"), 
	                formattedQuotationData.get("Cost"), 
	                formattedQuotationData.get("Weight"), 
	                formattedQuotationData.get("Transit Time")));

	            formattedData.put("quotationData", formattedQuotationData);
	        }

	        return formattedData;
	    }).collect(Collectors.toList());

	    // Create the final response map
	    Map<String, Object> response = new HashMap<>();
	    //response.put("status", 1);
	    //response.put("message", "success");
	    response.put("data", responseData);

	    return ResponseEntity.ok(response);
	}


	}

