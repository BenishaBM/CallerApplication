package com.annular.callerApplication.Service;

import java.util.List;

import com.annular.callerApplication.model.Group;
import com.annular.callerApplication.webModel.GroupDetailsWebModel;
import com.annular.callerApplication.webModel.GroupResponse;

public interface GroupDetailsService {

	Group saveGroupDetails(GroupDetailsWebModel groupDetailsWebModel);

	List<GroupResponse> getAllGroupDetails();

}
