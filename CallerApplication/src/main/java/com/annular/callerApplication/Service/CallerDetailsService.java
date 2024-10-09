package com.annular.callerApplication.Service;

import java.util.List;

import com.annular.callerApplication.model.CallerDetails;
import com.annular.callerApplication.webModel.CallerDetailsWebModel;

public interface CallerDetailsService {

	CallerDetails saveCallerDetails(CallerDetailsWebModel callerDetailsWebModel);

	CallerDetails getCallerDetailsById(String id);

	List<CallerDetails> getAllActiveDetails();

	List<CallerDetails> getAllMobileDetails(String mobileNumber);

	boolean checkGroupCodeExists(String groupCode);

}
